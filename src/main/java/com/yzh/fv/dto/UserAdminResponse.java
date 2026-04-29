package com.yzh.fv.dto;

import com.yzh.fv.entity.User;
import lombok.Data;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;

/**
 * 管理端用户响应DTO
 * 用于封装用户管理页面所需的数据
 */
@Data
public class UserAdminResponse {
    // 用户ID
    private Long id;
    // 用户名
    private String name;
    // 手机号
    private String phone;
    // 状态
    private Integer status;
    // 创建时间
    private LocalDateTime createTime;
    // 格式化后的创建时间字符串
    private String formattedCreateTime;
    // 是否新用户（7天内注册）
    private Boolean ifNew;
    // 优惠券是否已发放
    private Boolean voucherIssued = false;

    /**
     * 构造函数，从User实体创建响应对象
     * @param user 用户实体
     */
    public UserAdminResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.phone = user.getPhone();
        this.status = user.getStatus();
        this.createTime = user.getCreateTime();
        
        // 格式化创建时间
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.formattedCreateTime = user.getCreateTime().format(formatter);
        
        // 判断是否为新用户（7天内注册）
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minus(7, ChronoUnit.DAYS);
        this.ifNew = user.getCreateTime().isAfter(sevenDaysAgo);
    }
}