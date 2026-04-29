package com.yzh.fv.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yzh.fv.dto.DataScreenDto;
import com.yzh.fv.dto.DataScreenDto.TopSalesItem;
import com.yzh.fv.dto.DataScreenDto.DailySalesData;
import com.yzh.fv.dto.DataScreenDto.CategorySalesData;
import com.yzh.fv.entity.*;
import com.yzh.fv.mapper.*;
import com.yzh.fv.service.DataScreenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 数据大屏服务实现类
 */
@Service
@Slf4j
public class DataScreenServiceImpl implements DataScreenService {

    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private OrderMapper ordersMapper;
    
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    
    @Autowired
    private DishMapper dishMapper;
    
    @Autowired
    private SetmealMapper setmealMapper;
    
    @Autowired
    private CategoryMapper categoryMapper;
    
    @Override
    public DataScreenDto getDataScreenInfo() {
        DataScreenDto dataScreenDto = new DataScreenDto();
        
        // 获取总用户数
        Integer totalUserCount = userMapper.selectCount(null);
        dataScreenDto.setTotalUserCount(totalUserCount);
        
        // 获取今日新增用户数
        // 由于User实体类没有createTime字段，暂时设置为0
        // 现在有createTime了
        LocalDateTime todayStart = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        LambdaQueryWrapper<User> userWrapper = new LambdaQueryWrapper<>();
        userWrapper.ge(User::getCreateTime, todayStart);
        Integer todayNewUserCount = userMapper.selectCount(userWrapper);
        dataScreenDto.setTodayNewUserCount(todayNewUserCount);
        
        // 获取总订单数
        LambdaQueryWrapper<Orders> orderWrapper = new LambdaQueryWrapper<>();
        orderWrapper.ne(Orders::getStatus, 6); // 排除已取消的订单
        Integer totalOrderCount = ordersMapper.selectCount(orderWrapper);
        dataScreenDto.setTotalOrderCount(totalOrderCount);
        
        // 获取今日订单数
        LocalDateTime todayStartOrders = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        LambdaQueryWrapper<Orders> todayOrderWrapper = new LambdaQueryWrapper<>();
        todayOrderWrapper.ge(Orders::getOrderTime, todayStartOrders)
                         .ne(Orders::getStatus, 6);
        Integer todayOrderCount = ordersMapper.selectCount(todayOrderWrapper);
        dataScreenDto.setTodayOrderCount(todayOrderCount);
        
        // 获取总销售额
        List<Orders> allOrders = ordersMapper.selectList(orderWrapper);
        BigDecimal totalSalesAmount = allOrders.stream()
                .map(Orders::getAmount)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        dataScreenDto.setTotalSalesAmount(totalSalesAmount);
        
        // 获取今日销售额
        List<Orders> todayOrders = ordersMapper.selectList(todayOrderWrapper);
        BigDecimal todaySalesAmount = todayOrders.stream()
                .map(Orders::getAmount)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        dataScreenDto.setTodaySalesAmount(todaySalesAmount);
        
        // 获取正在配送的订单数（假设状态为3或4表示正在配送）
        LambdaQueryWrapper<Orders> deliveringWrapper = new LambdaQueryWrapper<>();
        deliveringWrapper.in(Orders::getStatus, Collections.singletonList(3));
        Integer deliveringOrderCount = ordersMapper.selectCount(deliveringWrapper);
        dataScreenDto.setDeliveringOrderCount(deliveringOrderCount);
        
        // 获取订单完成数（假设状态为5表示已完成）
        LambdaQueryWrapper<Orders> completedWrapper = new LambdaQueryWrapper<>();
        completedWrapper.eq(Orders::getStatus, 4);
        Integer completedOrderCount = ordersMapper.selectCount(completedWrapper);
        dataScreenDto.setCompletedOrderCount(completedOrderCount);
        
        // 获取销量前十的商品
        List<TopSalesItem> topSalesList = getTopSalesList();
        dataScreenDto.setTopSalesList(topSalesList);
        
        // 获取最近7天的销售数据（用于趋势图）
        List<DailySalesData> dailySales = getDailySalesData();
        dataScreenDto.setDailySales(dailySales);
        
        // 获取分类销售数据（用于饼图）
        List<CategorySalesData> categorySales = getCategorySalesData();
        dataScreenDto.setCategorySales(categorySales);
        
        return dataScreenDto;
    }
    
    /**
     * 获取销量前十的商品
     */
    private List<TopSalesItem> getTopSalesList() {
        // 查询所有订单详情
        List<OrderDetail> orderDetails = orderDetailMapper.selectList(null);
        
        // 按商品分组统计销量
        Map<Long, Map<String, Object>> productStats = new HashMap<>();
        
        for (OrderDetail detail : orderDetails) {
            Long productId = detail.getDishId() != null ? detail.getDishId() : detail.getSetmealId();
            if (productId == null) continue;
            
            Map<String, Object> stats = new HashMap<>();
            stats.put("name", detail.getName());
            stats.put("image", detail.getImage());
            stats.put("count", 0);
            stats.put("amount", BigDecimal.ZERO);
            productStats.putIfAbsent(productId, stats);
            
            stats = productStats.get(productId);
            stats.put("count", (int)stats.get("count") + detail.getNumber());
            stats.put("amount", ((BigDecimal)stats.get("amount")).add(detail.getAmount()));
        }
        
        // 转换为TopSalesItem列表并排序
        List<TopSalesItem> topSalesList = productStats.entrySet().stream()
                .map(entry -> {
                    TopSalesItem item = new TopSalesItem();
                    Map<String, Object> stats = entry.getValue();
                    item.setName((String) stats.get("name"));
                    item.setCount((int) stats.get("count"));
                    item.setImage((String) stats.get("image"));
                    item.setAmount((BigDecimal) stats.get("amount"));
                    return item;
                })
                .sorted(Comparator.comparing(TopSalesItem::getCount).reversed())
                .limit(10)
                .collect(Collectors.toList());
        
        return topSalesList;
    }
    
    /**
     * 获取最近7天的销售数据
     */
    private List<DailySalesData> getDailySalesData() {
        List<DailySalesData> dailySales = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");
        
        // 获取最近7天的日期
        for (int i = 6; i >= 0; i--) {
            LocalDate date = LocalDate.now().minusDays(i);
            String dateStr = date.format(formatter);
            
            // 查询当天的销售额
            LocalDateTime dayStart = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime dayEnd = LocalDateTime.of(date.plusDays(1), LocalTime.MIN);
            
            LambdaQueryWrapper<Orders> dayOrderWrapper = new LambdaQueryWrapper<>();
            dayOrderWrapper.between(Orders::getOrderTime, dayStart, dayEnd)
                          .ne(Orders::getStatus, 6); // 排除已取消的订单
            
            List<Orders> dayOrders = ordersMapper.selectList(dayOrderWrapper);
            BigDecimal sales = dayOrders.stream()
                    .map(Orders::getAmount)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            
            DailySalesData data = new DailySalesData();
            data.setDay(dateStr);
            data.setSales(sales);
            dailySales.add(data);
        }
        
        return dailySales;
    }
    
    /**
     * 获取分类销售数据
     */
    private List<CategorySalesData> getCategorySalesData() {
        // 查询所有订单详情
        List<OrderDetail> orderDetails = orderDetailMapper.selectList(null);
        
        // 获取所有订单信息（用于关联订单状态）
        Map<Long, Orders> orderMap = ordersMapper.selectList(null).stream()
                .filter(order -> order.getStatus() != 6) // 排除已取消的订单
                .collect(Collectors.toMap(Orders::getId, order -> order));
        
        // 获取所有菜品信息
        Map<Long, Dish> dishMap = dishMapper.selectList(null).stream()
                .collect(Collectors.toMap(Dish::getId, dish -> dish));
        
        // 获取所有套餐信息
        Map<Long, Setmeal> setmealMap = setmealMapper.selectList(null).stream()
                .collect(Collectors.toMap(Setmeal::getId, setmeal -> setmeal));
        
        // 获取所有分类信息
        Map<Long, Category> categoryMap = categoryMapper.selectList(null).stream()
                .collect(Collectors.toMap(Category::getId, category -> category));
        
        // 按分类分组计算销售额
        Map<Long, BigDecimal> categorySalesMap = new HashMap<>();
        
        for (OrderDetail detail : orderDetails) {
            // 检查订单是否有效
            Orders order = orderMap.get(detail.getOrderId());
            if (order == null) continue;
            
            Long categoryId = null;
            if (detail.getDishId() != null) {
                Dish dish = dishMap.get(detail.getDishId());
                if (dish != null) {
                    categoryId = dish.getCategoryId();
                }
            } else if (detail.getSetmealId() != null) {
                Setmeal setmeal = setmealMap.get(detail.getSetmealId());
                if (setmeal != null) {
                    categoryId = setmeal.getCategoryId();
                }
            }
            
            if (categoryId != null) {
                BigDecimal itemSales = detail.getAmount().multiply(new BigDecimal(detail.getNumber()));
                categorySalesMap.put(categoryId, categorySalesMap.getOrDefault(categoryId, BigDecimal.ZERO).add(itemSales));
            }
        }
        
        // 转换为CategorySalesData列表
        return categorySalesMap.entrySet().stream()
                .map(entry -> {
                    CategorySalesData data = new CategorySalesData();
                    Long categoryId = entry.getKey();
                    Category category = categoryMap.get(categoryId);
                    
                    if (category != null) {
                        data.setCategoryName(category.getName());
                    } else {
                        data.setCategoryName("未分类");
                    }
                    data.setSales(entry.getValue());
                    return data;
                })
                .sorted(Comparator.comparing(CategorySalesData::getSales).reversed())
                .collect(Collectors.toList());
    }
}