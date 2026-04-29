package com.yzh.fv.controller;

import com.yzh.fv.common.R;
import com.yzh.fv.entity.Dish;
import com.yzh.fv.entity.Evaluate;
import com.yzh.fv.service.EvaluateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户端评价控制器
 */
@RestController
@RequestMapping("/user/evaluate")
@Slf4j
public class UserEvaluateController {

    @Autowired
    private EvaluateService evaluateService;
    
    /**
     * 新增评价
     * @param evaluate 评价实体
     * @return 操作结果
     */
    @PostMapping("/save")
    public R<String> save(@RequestBody Evaluate evaluate) {
        log.info("新增评价: {}", evaluate);
        
        evaluateService.saveEvaluate(evaluate);
        return R.success("评价成功");
    }
    
    /**
     * 根据评价对象id查询评价列表
     * @param type 评价类型：1-菜品评价 2-套餐评价
     * @param targetId 评价对象id（dishId或setmealId）
     * @return 评价列表
     */
    @GetMapping("/list")
    public R<List<Evaluate>> list(Integer type, Long targetId) {
        log.info("查询评价列表: type={}, targetId={}", type, targetId);
        
        List<Evaluate> evaluateList = evaluateService.getByTargetId(type, targetId);
        return R.success(evaluateList);
    }
    
    /**
     * 获取评价对象的平均评分
     * @param type 评价类型：1-菜品评价 2-套餐评价
     * @param targetId 评价对象id（dishId或setmealId）
     * @return 平均评分
     */
    @GetMapping("/avg-rating")
    public R<Double> getAverageRating(Integer type, Long targetId) {
        log.info("获取平均评分: type={}, targetId={}", type, targetId);
        
        Double averageRating = evaluateService.getAverageRating(type, targetId);
        return R.success(averageRating);
    }
    
    /**
     * 获取用户的评价历史
     * @param userId 用户id（可选，默认获取当前登录用户）
     * @return 评价列表
     */
    @GetMapping("/user-history")
    public R<List<Evaluate>> getUserHistory(Long userId) {
        log.info("获取用户评价历史: userId={}", userId);
        
        // 如果没有指定userId，使用当前登录用户的id
        if (userId == null) {
            userId = com.yzh.fv.common.BaseContext.getCurrentId();
        }
        
        List<Evaluate> evaluateList = evaluateService.getByUserId(userId);
        return R.success(evaluateList);
    }


}