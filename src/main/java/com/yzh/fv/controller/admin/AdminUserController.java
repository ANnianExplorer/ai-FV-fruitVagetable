package com.yzh.fv.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yzh.fv.common.R;
import com.yzh.fv.dto.UserAdminResponse;
import com.yzh.fv.entity.User;
import com.yzh.fv.service.UserService;
import com.yzh.fv.service.VoucherUserServer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

/**
 * 管理端用户控制器
 * 提供用户管理相关接口
 */
@RestController
@RequestMapping("/api/user")
@Slf4j
public class AdminUserController {

    @Resource
    private UserService userService;
    
    @Resource
    private VoucherUserServer voucherUserServer;

    /**
     * 获取用户列表（支持分页、手机号搜索、新用户筛选）
     * @param page 页码
     * @param pageSize 每页条数
     * @param phone 手机号（可选）
     * @param isNewUser 是否仅显示新用户（可选）
     * @return 分页用户列表
     */
    @GetMapping("/list")
    public R<Page<UserAdminResponse>> getUserList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            String phone,
            Boolean isNewUser) {
        
        log.info("获取用户列表，页码：{}，每页条数：{}，手机号：{}，仅新用户：{}", page, pageSize, phone, isNewUser);
        
        // 创建分页构造器
        Page<User> pageInfo = new Page<>(page, pageSize);
        
        // 创建查询条件
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        
        // 手机号模糊查询
        if (StringUtils.isNotEmpty(phone)) {
            queryWrapper.like(User::getPhone, phone);
        }
        
        // 如果只查询新用户（7天内注册的用户）
        if (Boolean.TRUE.equals(isNewUser)) {
            LocalDateTime sevenDaysAgo = LocalDateTime.now().minus(7, ChronoUnit.DAYS);
            queryWrapper.gt(User::getCreateTime, sevenDaysAgo);
        }
        
        // 按创建时间倒序排序
        queryWrapper.orderByDesc(User::getCreateTime);
        
        // 执行分页查询
        userService.page(pageInfo, queryWrapper);
        
        // 创建返回结果页
        Page<UserAdminResponse> resultPage = new Page<>();
        resultPage.setTotal(pageInfo.getTotal());
        resultPage.setCurrent(pageInfo.getCurrent());
        resultPage.setSize(pageInfo.getSize());
        
        // 将User对象转换为UserAdminResponse对象
        List<UserAdminResponse> records = pageInfo.getRecords().stream()
                .map(UserAdminResponse::new)
                .collect(java.util.stream.Collectors.toList());
        resultPage.setRecords(records);
        
        return R.success(resultPage);
    }
    
    /**
     * 更新用户状态
     * @param params 更新参数
     * @return 更新结果
     */
    @PostMapping("/update/status")
    public R<String> updateUserStatus(@RequestBody Map<String, Object> params) {
        try {
            Long id = Long.valueOf(params.get("id").toString());
            Integer status = Integer.valueOf(params.get("status").toString());
            
            log.info("更新用户状态，用户ID：{}，新状态：{}", id, status);
            
            User user = new User();
            user.setId(id);
            user.setStatus(status);
            user.setUpdateTime(LocalDateTime.now());
            
            boolean success = userService.updateById(user);
            
            if (success) {
                return R.success("用户状态更新成功");
            } else {
                return R.error("用户状态更新失败");
            }
        } catch (Exception e) {
            log.error("更新用户状态异常", e);
            return R.error("更新失败：" + e.getMessage());
        }
    }
    
    /**
     * 发放新人优惠券给指定用户
     * @param params 发放参数
     * @return 发放结果
     */
    @PostMapping("/voucher/issue/new-user")
    public R<String> issueNewUserCoupon(@RequestBody Map<String, Object> params) {
        try {
            Long userId = Long.valueOf(params.get("userId").toString());
            
            log.info("发放新人优惠券给用户：{}", userId);
            
            // 验证用户是否存在
            User user = userService.getById(userId);
            if (user == null) {
                return R.error("用户不存在");
            }
            
            // 验证是否为新用户（7天内注册）
            LocalDateTime sevenDaysAgo = LocalDateTime.now().minus(7, ChronoUnit.DAYS);
            if (user.getCreateTime().isBefore(sevenDaysAgo)) {
                return R.error("该用户不是新用户，无法发放新人优惠券");
            }
            
            // 调用优惠券服务发放新人优惠券
            voucherUserServer.issueNewUserCoupons(userId);
            
            return R.success("新人优惠券发放成功");
        } catch (Exception e) {
            log.error("发放新人优惠券异常", e);
            return R.error("发放失败：" + e.getMessage());
        }
    }
    
    /**
     * 批量发放新人优惠券
     * @param params 批量发放参数
     * @return 发放结果
     */
    @PostMapping("/voucher/issue/batch")
    public R<String> batchIssueNewUserCoupons(@RequestBody Map<String, Object> params) {
        try {
            List<Long> userIds = (List<Long>) params.get("userIds");
            
            if (userIds == null || userIds.isEmpty()) {
                return R.error("请选择要发放优惠券的用户");
            }
            
            log.info("批量发放新人优惠券，用户数量：{}", userIds.size());
            
            LocalDateTime sevenDaysAgo = LocalDateTime.now().minus(7, ChronoUnit.DAYS);
            int successCount = 0;
            int failCount = 0;
            
            // 遍历用户列表，逐个发放
            for (Long userId : userIds) {
                try {
                    // 验证用户是否存在且为新用户
                    User user = userService.getById(userId);
                    if (user != null && user.getCreateTime().isAfter(sevenDaysAgo)) {
                        voucherUserServer.issueNewUserCoupons(userId);
                        successCount++;
                    } else {
                        failCount++;
                    }
                } catch (Exception e) {
                    log.error("发放优惠券给用户 {} 失败", userId, e);
                    failCount++;
                }
            }
            
            return R.success("批量发放完成，成功：" + successCount + "，失败：" + failCount);
        } catch (Exception e) {
            log.error("批量发放新人优惠券异常", e);
            return R.error("批量发放失败：" + e.getMessage());
        }
    }
    
    /**
     * 删除用户（软删除）
     * @param params 删除参数
     * @return 删除结果
     */
    @DeleteMapping("/delete")
    public R<String> deleteUser(@RequestParam Map<String, Object> params) {
        try {
            Long id = Long.valueOf(params.get("id").toString());
            log.info("删除用户，用户ID：{}", id);
            
            // 这里使用禁用代替删除
            User user = new User();
            user.setId(id);
            user.setStatus(0); // 0表示禁用
            user.setUpdateTime(LocalDateTime.now());
            
            boolean success = userService.updateById(user);
            
            if (success) {
                return R.success("用户已禁用");
            } else {
                return R.error("操作失败");
            }
        } catch (Exception e) {
            log.error("删除用户异常", e);
            return R.error("操作失败：" + e.getMessage());
        }
    }
}