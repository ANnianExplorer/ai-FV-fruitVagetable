package com.yzh.fv.controller.user;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yzh.fv.common.R;
import com.yzh.fv.entity.Dish;
import com.yzh.fv.entity.OrderDetail;
import com.yzh.fv.entity.Orders;
import com.yzh.fv.entity.Setmeal;
import com.yzh.fv.service.DishService;
import com.yzh.fv.service.OrderDetailService;
import com.yzh.fv.service.OrdersService;
import com.yzh.fv.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;


/**
 * 用户端商品控制器
 */
@RestController
@RequestMapping("/user/product")
public class UserProductController {

    @Autowired
    private DishService dishService;
    
    @Autowired
    private SetmealService setmealService;
    
    @Autowired
    private OrderDetailService orderDetailService;
    
    @Autowired
    private OrdersService orderService;

    /**
     * 获取商品销量排行榜
     * @param type 排行榜类型：daily(日榜)、weekly(周榜)、monthly(月榜)
     * @return 排行榜数据
     */
    @GetMapping("/sales-ranking")
    public R<List<Map<String, Object>>> getSalesRanking(@RequestParam(defaultValue = "daily") String type) {
        // 计算时间范围
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startTime;
        
        switch (type) {
            case "weekly":
                // 周榜：最近7天
                startTime = now.minusDays(7);
                break;
            case "monthly":
                // 月榜：最近30天
                startTime = now.minusDays(30);
                break;
            case "daily":
            default:
                // 日榜：今天
                startTime = now.truncatedTo(ChronoUnit.DAYS);
                break;
        }
        
        // 查询已完成订单的订单明细，统计销量
        // 1. 先查询已完成的订单
        LambdaQueryWrapper<Orders> orderWrapper = new LambdaQueryWrapper<>();
        orderWrapper.eq(Orders::getStatus, 4); // 4表示已完成的订单
        orderWrapper.ge(Orders::getCheckoutTime, startTime);
        List<Orders> completedOrders = orderService.list(orderWrapper);
        
        if (completedOrders.isEmpty()) {
            return R.success(new ArrayList<>());
        }
        
        // 2. 获取已完成订单的ID列表
        List<Long> orderIds = completedOrders.stream()
                .map(Orders::getId)
                .collect(Collectors.toList());
        
        // 3. 查询这些订单的明细
        LambdaQueryWrapper<OrderDetail> detailWrapper = new LambdaQueryWrapper<>();
        detailWrapper.in(OrderDetail::getOrderId, orderIds);
        List<OrderDetail> orderDetails = orderDetailService.list(detailWrapper);
        
        // 4. 统计销量：创建一个Map来存储ID到销量的映射
        Map<Long, Integer> dishSalesMap = new HashMap<>();
        Map<Long, Integer> setmealSalesMap = new HashMap<>();
        
        // 统计每个菜品和套餐的销量
        for (OrderDetail detail : orderDetails) {
            if (detail.getDishId() != null) {
                // 菜品销量统计
                dishSalesMap.put(detail.getDishId(), 
                        dishSalesMap.getOrDefault(detail.getDishId(), 0) + detail.getNumber());
            } else if (detail.getSetmealId() != null) {
                // 套餐销量统计
                setmealSalesMap.put(detail.getSetmealId(), 
                        setmealSalesMap.getOrDefault(detail.getSetmealId(), 0) + detail.getNumber());
            }
        }
        
        // 5. 获取所有需要展示的商品信息
        List<Map<String, Object>> productList = new ArrayList<>();
        
        // 添加菜品信息
        if (!dishSalesMap.isEmpty()) {
            LambdaQueryWrapper<Dish> dishWrapper = new LambdaQueryWrapper<>();
            dishWrapper.in(Dish::getId, dishSalesMap.keySet());
            dishWrapper.eq(Dish::getStatus, 1); // 只显示上架的菜品
            List<Dish> dishList = dishService.list(dishWrapper);
            
            for (Dish dish : dishList) {
                Map<String, Object> productMap = new HashMap<>();
                productMap.put("id", dish.getId());
                productMap.put("productName", dish.getName());
                productMap.put("price", dish.getPrice());
                productMap.put("salesCount", dishSalesMap.getOrDefault(dish.getId(), 0));
                productMap.put("imageUrl", dish.getImage() != null ? dish.getImage() : "/front/images/noImg.png");
                productMap.put("type", "dish"); // 标识为菜品
                productList.add(productMap);
            }
        }
        
        // 添加套餐信息
        if (!setmealSalesMap.isEmpty()) {
            LambdaQueryWrapper<Setmeal> setmealWrapper = new LambdaQueryWrapper<>();
            setmealWrapper.in(Setmeal::getId, setmealSalesMap.keySet());
            setmealWrapper.eq(Setmeal::getStatus, 1); // 只显示上架的套餐
            List<Setmeal> setmealList = setmealService.list(setmealWrapper);
            
            for (Setmeal setmeal : setmealList) {
                Map<String, Object> productMap = new HashMap<>();
                productMap.put("id", setmeal.getId());
                productMap.put("productName", setmeal.getName());
                productMap.put("price", setmeal.getPrice());
                productMap.put("salesCount", setmealSalesMap.getOrDefault(setmeal.getId(), 0));
                productMap.put("imageUrl", setmeal.getImage() != null ? setmeal.getImage() : "/front/images/noImg.png");
                productMap.put("type", "setmeal"); // 标识为套餐
                productList.add(productMap);
            }
        }
        
        // 6. 如果没有销售数据，则返回热门商品
        if (productList.isEmpty()) {
            return getHotProducts();
        }
        
        // 7. 按销量排序，取前10名
        productList.sort((a, b) -> {
            int salesA = (Integer) a.get("salesCount");
            int salesB = (Integer) b.get("salesCount");
            return salesB - salesA; // 降序排序
        });
        
        // 取前10名
        List<Map<String, Object>> top10Products = productList.stream()
                .limit(10)
                .collect(Collectors.toList());
        
        return R.success(top10Products);
    }
    
    /**
     * 获取热门商品（当没有销售数据时使用）
     */
    private R<List<Map<String, Object>>> getHotProducts() {
        List<Map<String, Object>> productList = new ArrayList<>();
        
        // 获取热门菜品
        LambdaQueryWrapper<Dish> dishWrapper = new LambdaQueryWrapper<>();
        dishWrapper.eq(Dish::getStatus, 1);
        dishWrapper.orderByDesc(Dish::getSales);
        dishWrapper.last("LIMIT 5");
        List<Dish> hotDishes = dishService.list(dishWrapper);
        
        for (Dish dish : hotDishes) {
            Map<String, Object> productMap = new HashMap<>();
            productMap.put("id", dish.getId());
            productMap.put("productName", dish.getName());
            productMap.put("price", dish.getPrice());
            productMap.put("salesCount", dish.getSales());
            productMap.put("imageUrl", dish.getImage() != null ? dish.getImage() : "/front/images/noImg.png");
            productMap.put("type", "dish");
            productList.add(productMap);
        }
        
        // 获取热门套餐
        LambdaQueryWrapper<Setmeal> setmealWrapper = new LambdaQueryWrapper<>();
        setmealWrapper.eq(Setmeal::getStatus, 1);
        setmealWrapper.orderByDesc(Setmeal::getUpdateTime); // 按更新时间排序作为热度参考
        setmealWrapper.last("LIMIT 5");
        List<Setmeal> hotSetmeals = setmealService.list(setmealWrapper);
        
        for (Setmeal setmeal : hotSetmeals) {
            Map<String, Object> productMap = new HashMap<>();
            productMap.put("id", setmeal.getId());
            productMap.put("productName", setmeal.getName());
            productMap.put("price", setmeal.getPrice());
            productMap.put("salesCount", 0); // 没有销量数据时显示0
            productMap.put("imageUrl", setmeal.getImage() != null ? setmeal.getImage() : "/front/images/noImg.png");
            productMap.put("type", "setmeal");
            productList.add(productMap);
        }
        
        return R.success(productList);
    }
}