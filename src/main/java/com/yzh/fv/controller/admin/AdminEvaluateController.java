package com.yzh.fv.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yzh.fv.common.R;
import com.yzh.fv.entity.Evaluate;
import com.yzh.fv.service.EvaluateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 管理端评价控制器
 */
@RestController
@RequestMapping("/admin/evaluate")
@Slf4j
public class AdminEvaluateController {

    private static final Logger log = LoggerFactory.getLogger(AdminEvaluateController.class);
    
    @Autowired
    private EvaluateService evaluateService;
    
    /**
     * 分页查询评价列表
     * @param page 当前页
     * @param pageSize 每页大小
     * @param type 评价类型：1-菜品评价 2-套餐评价
     * @param targetId 评价对象id（dishId或setmealId）
     * @return 评价列表
     */
    @GetMapping("/page")
    public R<Page<Evaluate>> page(@RequestParam(defaultValue = "1") int page,
                                 @RequestParam(defaultValue = "10") int pageSize,
                                 Integer type,
                                 Long targetId) {
        log.info("分页查询评价列表: page={}, pageSize={}, type={}, targetId={}", 
                 page, pageSize, type, targetId);
        
        Page<Evaluate> evaluatePage = evaluateService.pageQuery(page, pageSize, type, targetId);
        return R.success(evaluatePage);
    }
    
    /**
     * 回复评价
     * @param map 评价id和回复内容
     * @return 操作结果
     */
    @PostMapping("/reply")
    public R<String> reply(@RequestBody Map<String, Object> map) {
        Long id = Long.valueOf(map.get("id").toString());
        String reply = (String) map.get("reply");
        
        log.info("回复评价: id={}, reply={}", id, reply);
        
        evaluateService.replyEvaluate(id, reply);
        return R.success("回复成功");
    }
    
    /**
     * 删除评价
     * @param id 评价id
     * @return 操作结果
     */
    @DeleteMapping("/{id}")
    public R<String> delete(@PathVariable Long id) {
        log.info("删除评价: id={}", id);
        
        evaluateService.removeById(id);
        return R.success("删除成功");
    }
    
    /**
     * 获取评价统计数据
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 评价统计数据
     */
    @GetMapping("/statistics")
    public R<Map<String, Object>> getEvaluateStatistics(@RequestParam(required = false) String startDate, 
                                                       @RequestParam(required = false) String endDate) {
        
        log.info("获取评价统计数据: startDate={}, endDate={}", startDate, endDate);
        
        LambdaQueryWrapper<Evaluate> queryWrapper = new LambdaQueryWrapper<>();
        
        // 设置时间范围查询条件
        if (startDate != null) {
            queryWrapper.ge(Evaluate::getEvaluateTime, LocalDate.parse(startDate).atStartOfDay());
        }
        if (endDate != null) {
            queryWrapper.le(Evaluate::getEvaluateTime, LocalDate.parse(endDate).atTime(LocalTime.MAX));
        }
        
        // 查询符合条件的所有评价
        List<Evaluate> evaluateList = evaluateService.list(queryWrapper);
        
        // 统计总评价数
        long totalEvaluates = evaluateList.size();
        
        // 计算平均评分
        double averageRating = evaluateList.stream()
                .mapToInt(Evaluate::getRating)
                .average()
                .orElse(0.0);
        
        // 统计今日评价数
        LocalDateTime todayStart = LocalDate.now().atStartOfDay();
        long todayEvaluates = evaluateService.count(new LambdaQueryWrapper<Evaluate>()
                .ge(Evaluate::getEvaluateTime, todayStart));
        
        // 统计已回复评价数
        long repliedEvaluates = evaluateList.stream()
                .filter(e -> e.getReply() != null && !e.getReply().isEmpty())
                .count();
        
        // 获取待回复评价列表
        List<Map<String, Object>> pendingReplyList = evaluateList.stream()
                .filter(e -> e.getReply() == null || e.getReply().isEmpty())
                .limit(10)
                .map(e -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", e.getId());
                    map.put("targetName", e.getTargetName());
                    map.put("rating", e.getRating());
                    map.put("content", e.getContent());
                    map.put("evaluateTime", e.getEvaluateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                    return map;
                })
                .collect(Collectors.toList());
        
        // 统计评分分布
        Map<Integer, Long> ratingCountMap = evaluateList.stream()
                .collect(Collectors.groupingBy(Evaluate::getRating, Collectors.counting()));
        
        List<Map<String, Object>> ratingDistribution = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("star", i);
            map.put("count", ratingCountMap.getOrDefault(i, 0L));
            ratingDistribution.add(map);
        }
        
        // 生成最近7天的评价趋势
        List<Map<String, Object>> evaluateTrend = new ArrayList<>();
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");
        
        for (int i = 6; i >= 0; i--) {
            LocalDate date = now.minusDays(i);
            LocalDateTime startOfDay = date.atStartOfDay();
            LocalDateTime endOfDay = date.atTime(LocalTime.MAX);
            
            long count = evaluateService.count(new LambdaQueryWrapper<Evaluate>()
                    .ge(Evaluate::getEvaluateTime, startOfDay)
                    .le(Evaluate::getEvaluateTime, endOfDay));
            
            Map<String, Object> map = new HashMap<>();
            map.put("date", date.format(formatter));
            map.put("count", count);
            evaluateTrend.add(map);
        }
        
        // 构建返回数据
        Map<String, Object> result = new HashMap<>();
        result.put("totalEvaluates", totalEvaluates);
        result.put("averageRating", averageRating);
        result.put("todayEvaluates", todayEvaluates);
        result.put("repliedEvaluates", repliedEvaluates);
        result.put("pendingReplyList", pendingReplyList);
        result.put("ratingDistribution", ratingDistribution);
        result.put("evaluateTrend", evaluateTrend);
        
        return R.success(result);
    }
}