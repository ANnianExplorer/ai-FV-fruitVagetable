package com.yzh.fv.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

/**
 * 数据大屏展示DTO
 */
@Data
public class DataScreenDto {
    // 总用户数
    private Integer totalUserCount;
    
    // 今日新增用户数
    private Integer todayNewUserCount;
    
    // 总订单数
    private Integer totalOrderCount;
    
    // 今日订单数
    private Integer todayOrderCount;
    
    // 总销售额
    private BigDecimal totalSalesAmount;
    
    // 今日销售额
    private BigDecimal todaySalesAmount;
    
    // 正在配送的订单数
    private Integer deliveringOrderCount;
    
    // 订单完成数
    private Integer completedOrderCount;
    
    // 果蔬销量前十排行榜
    private List<TopSalesItem> topSalesList;
    
    // 每日销售数据（用于趋势图）
    private List<DailySalesData> dailySales;
    
    // 分类销售数据（用于饼图）
    private List<CategorySalesData> categorySales;
    
    // 销量排行榜项
    @Data
    public static class TopSalesItem {
        // 商品名称
        private String name;
        
        // 销量
        private Integer count;
        
        // 图片
        private String image;
        
        // 总金额
        private BigDecimal amount;
    }
    
    /**
     * 每日销售数据
     */
    @Data
    public static class DailySalesData {
        // 日期
        private String day;
        // 销售额
        private BigDecimal sales;
    }

    /**
     * 分类销售数据
     */
    @Data
    public static class CategorySalesData {
        // 分类名称
        private String categoryName;
        // 销售额
        private BigDecimal sales;
    }
}