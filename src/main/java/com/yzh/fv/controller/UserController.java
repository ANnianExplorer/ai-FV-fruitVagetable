package com.yzh.fv.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yzh.fv.common.R;
import com.yzh.fv.entity.InfoUser;
import com.yzh.fv.entity.User;
import com.yzh.fv.entity.VoucherUser;
import com.yzh.fv.service.InfoUserService;
import com.yzh.fv.service.UserService;
import com.yzh.fv.service.VoucherUserServer;
import com.yzh.fv.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author 杨振华
 * @since 2023/1/15
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class    UserController {

    /**==============
     * 这里需要修改
     * front/api/login.js
     * 和front/page/login.html
     * 这两个文件里面的内容
     * ==============
     */

    @Resource
    private UserService userService;

    @Resource
    private VoucherUserServer voucherUserServer;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private InfoUserService infoUserService;


    /**
     * 发送手机短信验证码
     * @param user
     * @return
     */
    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session) throws MessagingException {
        //获取手机号
        String phone = user.getPhone();

        if(StringUtils.isNotEmpty(phone)){

            //region 原来的短信验证代码
            //生成随机的4位验证码
            String code = ValidateCodeUtils.generateValidateCode(4).toString();

            log.info("code={}",code);

            //调用阿里云提供的短信服务API完成发送短信
            //SMSUtils.sendMessage("瑞吉外卖","",phone,code);

            //需要将生成的验证码保存到Session
            session.setAttribute(phone,code);

            // 将生成的验证码缓存到Redis中，并且设置有效期为5分钟
            redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);

            //endregion

            //region 邮箱验证
            /**
            /* * 现在为邮箱验证代码
             */
            /*// 随机生成验证码
            String code = MailUtils.achieveCode();
            log.info("验证码：{}",code);
            // 这里的phone就是邮箱，code是生成的验证码
            MailUtils.sendTestMail(phone,code);
            session.setAttribute(phone,code);*/
            //endregion

            return R.success("验证码发送成功");
        }

        return R.error("验证码发送失败");
    }

    /**
     * 移动端用户登录
     * @param map
     * @param session
     * @return
     * 在这里将用户也绑定到优惠券-用户信息表上
     */
    @PostMapping("/login")
    public R<User> login(@RequestBody Map map, HttpSession session){
        log.info(map.toString());
        //获取手机号
        String phone = map.get("phone").toString();
        //获取验证码
        String code = map.get("code").toString();

        //从Session中获取保存的验证码
        Object codeInSession = session.getAttribute(phone);

        //从redis中获取缓存的验证码
        //Object codeInSession = redisTemplate.opsForValue().get(phone);

        //进行验证码的比对（页面提交的验证码和Session中保存的验证码比对）
        if(codeInSession != null && codeInSession.equals(code)){
            //如果能够比对成功，说明登录成功

            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone,phone);

            User user = userService.getOne(queryWrapper);
            if(user == null){
                //判断当前手机号对应的用户是否为新用户，如果是新用户就自动完成注册
                user = new User();
                user.setPhone(phone);
                user.setStatus(1);
                user.setName("用户"+codeInSession);
                userService.save(user);
                //==========================
                queryWrapper.eq(User::getPhone,phone);
                User userV = userService.getOne(queryWrapper);
                Long id = userV.getId();// 用户id
                log.info("登录用户id是======================"+String.valueOf(id));

                // 添加优惠券到关联表
                LambdaQueryWrapper<VoucherUser> queryWrapperV = new LambdaQueryWrapper<>();
                queryWrapperV.eq(VoucherUser::getUserId,id);
                VoucherUser vu = voucherUserServer.getOne(queryWrapperV);
                if (vu == null) {
                    vu = new VoucherUser();
                    vu.setUserId(id);
                    vu.setVoucherId(1664147932250677249L);// 新用户都有新用户优惠券
                    voucherUserServer.save(vu);
                }

                // 添加通知到关联表
                LambdaQueryWrapper<InfoUser> queryWrapperI = new LambdaQueryWrapper<>();
                queryWrapperI.eq(InfoUser::getUserId,id);
                InfoUser iu = infoUserService.getOne(queryWrapperI);
                if (iu == null) {
                    iu = new InfoUser();
                    iu.setUserId(id);
                    iu.setInfoId(1664148662764191745L);// 新用户通知
                    infoUserService.save(iu);
                }
                /*InfoUser infoUser = new InfoUser();
                infoUser.setUserId(id);
                infoUserService.save(infoUser);*/

            }
            session.setAttribute("user",user.getId());

            // 如果用户登录成功，删除验证码
            redisTemplate.delete(phone);

            return R.success(user);
        }
        return R.error("登录失败");
    }

    @PostMapping("/loginout")
    public R<String> logout(HttpSession session){
        session.removeAttribute("user");
        return R.success("退出成功！");
    }

}
