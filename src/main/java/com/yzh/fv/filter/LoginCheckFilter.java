package com.yzh.fv.filter;

import com.alibaba.fastjson.JSON;
import com.yzh.fv.common.BaseContext;
import com.yzh.fv.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 登录检查过滤器
 *
 * @author 杨振华
 * @since 2023/1/9
 */
@Slf4j
@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")
public class LoginCheckFilter implements Filter {

    // 路径匹配器，支持通配符
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //1、获取本次请求的URL
        String requestURI = request.getRequestURI();

        // 定义不需要处理的请求路径
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/user/sendMsg",
                "/user/login",
                "/doc.html",
                "/webjars/**",
                "/swagger-resources",
                "/v2/api-docs"
        };
        //2、 判断本次请求是否需要处理
        boolean check = check(urls, requestURI);

        //3、如果不需要处理，则直接放行
        if (check){
            log.info("本次请求{}不需要处理",requestURI);
            filterChain.doFilter(request,response);
            return;
        }

        //4、判断登录状态
        
        // 判断是否为用户端API路径（/user/开头但不是登录或发送消息接口）
        boolean isUserApi = requestURI.startsWith("/user/") && 
                           !requestURI.equals("/user/login") && 
                           !requestURI.equals("/user/sendMsg");
        
        // 如果是用户端API路径，必须使用用户登录状态，不允许使用管理端身份
        if (isUserApi) {
            Object userObj = request.getSession().getAttribute("user");
            if (userObj != null) {
                try {
                    Long userId = (Long) userObj;
                    log.info("移动端用户已登录，用户id为：{}", userId);
                    BaseContext.setCurrent(userId);
                    request.setAttribute("loginType", "user");
                    filterChain.doFilter(request, response);
                    return;
                } catch (ClassCastException e) {
                    log.error("用户ID类型转换错误: {}", e.getMessage());
                }
            }
            // 用户端API但用户未登录，直接返回未登录状态，不检查管理端登录
            log.info("用户端API路径用户未登录，请求URI: {}", requestURI);
            response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
            return;
        }
        
        // 非用户端API路径，检查管理端登录
        if (request.getSession().getAttribute("employee") != null){
            Long empId = (Long) request.getSession().getAttribute("employee");
            log.info("管理端用户已登录，用户id为：{}", empId);
            BaseContext.setCurrent(empId);
            request.setAttribute("loginType", "employee");
            filterChain.doFilter(request, response);
            return;
        }
        
        // 仅在非用户端API路径且管理员未登录时，最后检查移动端用户登录
        if (!isUserApi) {
            Object userObj = request.getSession().getAttribute("user");
            if (userObj != null) {
                try {
                    Long userId = (Long) userObj;
                    log.info("移动端用户已登录，用户id为：{}", userId);
                    BaseContext.setCurrent(userId);
                    request.setAttribute("loginType", "user");
                    filterChain.doFilter(request, response);
                    return;
                } catch (ClassCastException e) {
                    log.error("用户ID类型转换错误: {}", e.getMessage());
                }
            }
        }
        
        // 记录未登录状态的日志，包含请求URI以便于调试
        log.info("用户未登录，请求URI: {}", requestURI);

        //5、如果未登录则返回未登录结果
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;

    }

    public boolean check(String[] urls,String requestUrl){
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestUrl);
            if (match){
                return true;
            }
        }
        return false;
    }
}
