package com.yzh.fv.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yzh.fv.common.R;
import com.yzh.fv.entity.Info;
import com.yzh.fv.service.InfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 通知管理
 *
 * @author : xiao
 * @since ： 2023/5/14 19:36
 */
@RestController
@Slf4j
@RequestMapping("/info")
public class InfoController {

    @Resource
    private InfoService infoService;

    /**
     * 添加通知
     * @param info
     * @return
     */
    @PostMapping
    public R<String> addInfo(@RequestBody Info info){
        infoService.save(info);
        return R.success("通知添加成功！");
    }

    /**
     * 根据id删除通知
     * @param id
     * @return
     */
    @DeleteMapping
    public R<String> deleInfo(Long id){
        infoService.removeById(id);
        return R.success("删除成功！");
    }

    @GetMapping("/{id}")
    public R<Info> queryById(@PathVariable Long id){
        Info info = infoService.getById(id);
        return R.success(info);
    }
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
        //日志打印
        log.info("page={}, pageSize={}, name={}", page, pageSize, name);
        Page pageInfo = new Page(page, pageSize);
        //查询
        //添加排序条件
        LambdaQueryWrapper<Info> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .like(StringUtils.isNotEmpty(name),Info::getName,name)
                .orderByDesc(Info::getCreateTime);
        //执行查询
        infoService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }
}
