package com.yzh.fv.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yzh.fv.entity.Evaluate;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;

/**
 * 评价服务接口
 */
public interface EvaluateService extends IService<Evaluate> {
    
    /**
     * 新增评价
     * @param evaluate 评价实体
     */
    void saveEvaluate(Evaluate evaluate);
    
    /**
     * 分页查询评价列表
     * @param page 当前页
     * @param pageSize 每页大小
     * @param type 评价类型：1-菜品评价 2-套餐评价
     * @param targetId 评价对象id（dishId或setmealId）
     * @return 评价列表
     */
    Page<Evaluate> pageQuery(int page, int pageSize, Integer type, Long targetId);
    
    /**
     * 管理员回复评价
     * @param id 评价id
     * @param reply 回复内容
     */
    void replyEvaluate(Long id, String reply);
    
    /**
     * 根据评价对象id查询评价列表
     * @param type 评价类型：1-菜品评价 2-套餐评价
     * @param targetId 评价对象id（dishId或setmealId）
     * @return 评价列表
     */
    List<Evaluate> getByTargetId(Integer type, Long targetId);
    
    /**
     * 获取评价对象的平均评分
     * @param type 评价类型：1-菜品评价 2-套餐评价
     * @param targetId 评价对象id（dishId或setmealId）
     * @return 平均评分
     */
    Double getAverageRating(Integer type, Long targetId);
    
    /**
     * 根据用户id查询评价历史
     * @param userId 用户id
     * @return 评价列表
     */
    List<Evaluate> getByUserId(Long userId);
}