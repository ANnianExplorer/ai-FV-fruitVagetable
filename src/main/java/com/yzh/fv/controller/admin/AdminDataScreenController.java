package com.yzh.fv.controller.admin;

import com.yzh.fv.common.R;
import com.yzh.fv.dto.DataScreenDto;
import com.yzh.fv.service.DataScreenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 后台数据大屏控制器
 */
@RestController
@RequestMapping("/admin/datascreen")
@Slf4j
public class AdminDataScreenController {

    @Autowired
    private DataScreenService dataScreenService;

    /**
     * 获取数据大屏信息
     * @return 数据大屏信息
     */
    @GetMapping("/info")
    public R<Map<String, Object>> getDatascreenInfo() {
        log.info("获取数据大屏信息");

        // 使用专门的数据大屏服务获取数据
        DataScreenDto dataScreenDto = dataScreenService.getDataScreenInfo();
        
        // 构建返回结果，确保格式与前端期望一致
        Map<String, Object> result = new HashMap<>();
        
        // 1. 用户数据
        result.put("totalUsers", dataScreenDto.getTotalUserCount());
        result.put("todayNewUsers", dataScreenDto.getTodayNewUserCount());
        
        // 2. 订单数据
        result.put("totalOrders", dataScreenDto.getTotalOrderCount());
        result.put("todayOrders", dataScreenDto.getTodayOrderCount());
        result.put("deliveringOrders", dataScreenDto.getDeliveringOrderCount());
        result.put("completedOrders", dataScreenDto.getCompletedOrderCount());
        
        // 3. 销售数据
        result.put("totalSales", dataScreenDto.getTotalSalesAmount());
        result.put("todaySales", dataScreenDto.getTodaySalesAmount());
        
        // 4. 销量排行榜TOP10
        List<Map<String, Object>> salesTopList = dataScreenDto.getTopSalesList().stream()
                .map(item -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("name", item.getName());
                    map.put("value", item.getCount());
                    map.put("image", item.getImage());
                    return map;
                })
                .collect(Collectors.toList());
        result.put("top10", salesTopList);
        
        // 5. 销售趋势数据
        List<Map<String, Object>> salesTrendList = dataScreenDto.getDailySales().stream()
                .map(item -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("date", item.getDay());
                    map.put("value", item.getSales().doubleValue());
                    return map;
                })
                .collect(Collectors.toList());
        result.put("salesTrend", salesTrendList);
        
        // 6. 分类销售占比
        List<Map<String, Object>> categorySalesList = dataScreenDto.getCategorySales().stream()
                .map(item -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("name", item.getCategoryName());
                    map.put("value", item.getSales().doubleValue());
                    return map;
                })
                .collect(Collectors.toList());
        result.put("categoryDistribution", categorySalesList);

        return R.success(result);
    }
}