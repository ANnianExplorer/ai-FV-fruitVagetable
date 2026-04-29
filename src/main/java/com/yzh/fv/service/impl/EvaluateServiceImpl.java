package com.yzh.fv.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzh.fv.common.BaseContext;
import com.yzh.fv.entity.Evaluate;
import com.yzh.fv.mapper.EvaluateMapper;
import com.yzh.fv.service.EvaluateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 评价服务实现类
 */
@Service
@Slf4j
public class EvaluateServiceImpl extends ServiceImpl<EvaluateMapper, Evaluate> implements EvaluateService {

    @Override
    @Transactional
    public void saveEvaluate(Evaluate evaluate) {
        evaluate.setUserId(BaseContext.getCurrentId());
        evaluate.setEvaluateTime(LocalDateTime.now());
        evaluate.setCreateTime(LocalDateTime.now());
        evaluate.setUpdateTime(LocalDateTime.now());
        evaluate.setCreateUser(BaseContext.getCurrentId());
        evaluate.setUpdateUser(BaseContext.getCurrentId());
        evaluate.setIsDeleted(0);

        // 设置默认评分
        if (evaluate.getRating() == null) {
            evaluate.setRating(0);
        }

        this.save(evaluate);
        log.info("用户评价保存成功: {}", evaluate.getId());
    }

    @Override
    public Page<Evaluate> pageQuery(int page, int pageSize, Integer type, Long targetId) {
        Page<Evaluate> pageInfo = new Page<>(page, pageSize);
        LambdaQueryWrapper<Evaluate> queryWrapper = new LambdaQueryWrapper<>();
        
        // 设置查询条件
        if (type != null) {
            queryWrapper.eq(Evaluate::getType, type);
        }
        if (targetId != null) {
            queryWrapper.eq(Evaluate::getTargetId, targetId);
        }
        
        // 按评价时间倒序
        queryWrapper.orderByDesc(Evaluate::getEvaluateTime);
        
        return this.page(pageInfo, queryWrapper);
    }

    @Override
    @Transactional
    public void replyEvaluate(Long id, String reply) {
        Evaluate evaluate = this.getById(id);
        if (evaluate != null) {
            evaluate.setReply(reply);
            evaluate.setReplyTime(LocalDateTime.now());
            evaluate.setUpdateTime(LocalDateTime.now());
            evaluate.setUpdateUser(BaseContext.getCurrentId());
            
            this.updateById(evaluate);
            log.info("评价回复成功: {}", id);
        }
    }

    @Override
    public List<Evaluate> getByTargetId(Integer type, Long targetId) {
        LambdaQueryWrapper<Evaluate> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Evaluate::getType, type)
                   .eq(Evaluate::getTargetId, targetId)
                   .orderByDesc(Evaluate::getEvaluateTime);
        
        return this.list(queryWrapper);
    }

    @Override
    public Double getAverageRating(Integer type, Long targetId) {
        List<Evaluate> evaluateList = getByTargetId(type, targetId);
        if (evaluateList.isEmpty()) {
            return 0.0;
        }
        
        // 计算平均评分
        double totalRating = evaluateList.stream()
                .mapToInt(Evaluate::getRating)
                .sum();
        
        return Math.round((totalRating / evaluateList.size()) * 10) / 10.0; // 保留一位小数
    }
    
    @Override
    public List<Evaluate> getByUserId(Long userId) {
        LambdaQueryWrapper<Evaluate> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Evaluate::getUserId, userId)
                   .orderByDesc(Evaluate::getEvaluateTime);
        
        return this.list(queryWrapper);
    }
}