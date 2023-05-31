package com.yzh.fv.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yzh.fv.common.BaseContext;
import com.yzh.fv.common.R;
import com.yzh.fv.dto.InfoDto;
import com.yzh.fv.dto.OrdersDto;
import com.yzh.fv.entity.*;
import com.yzh.fv.mapper.InfoMapper;
import com.yzh.fv.mapper.InfoUserMapper;
import com.yzh.fv.service.InfoService;
import com.yzh.fv.service.InfoUserService;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

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

    @Resource
    private InfoUserService infoUserService;

    @Resource
    private InfoMapper infoMapper;

    @Resource
    private InfoUserMapper infoUserMapper;

    /**
     * 添加通知
     * @param info
     * @return
     */
    @PostMapping
    public R<String> addInfo(@RequestBody Info info){
        infoService.save(info);
        // 启用优惠劵后
        // 获取更新最近的一条优惠券
        LambdaQueryWrapper<Info>  queryWrapperI  =  new  LambdaQueryWrapper<>();
        queryWrapperI
                .orderByDesc(Info::getUpdateTime)
                .last("limit  1");
        Info one = infoMapper.selectOne(queryWrapperI);
        UpdateWrapper<InfoUser> userUpdateWrapper = new UpdateWrapper<>();
        userUpdateWrapper.set("info_id",one.getId());
        infoUserMapper.update(null, userUpdateWrapper);

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


    /**
     * 用户得到通知
     *
     * @return {@link R}<{@link String}>
     */
    @GetMapping("/list")
    public R<Page> page(int page,int pageSize){
        // 分页构造器
        Page<Info> pageInfo = new Page<>(page,pageSize);
        Page<InfoDto> infoDtoPage = new Page<>();

        // 用户ID
        Long currentId = BaseContext.getCurrentId();

        // 原条件写入
        LambdaQueryWrapper<InfoUser> queryWrapperIU = new LambdaQueryWrapper<>();
        queryWrapperIU
                .eq(InfoUser::getUserId,currentId);
        InfoUser infoUser = infoUserService.getOne(queryWrapperIU);

        LambdaQueryWrapper<Info> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(Info::getId,infoUser.getInfoId());

        infoService.page(pageInfo,queryWrapper);

        // 普通赋值
        BeanUtils.copyProperties(pageInfo,infoDtoPage,"records");

        // 订单赋值
        List<Info> records = pageInfo.getRecords();

        List<InfoDto> infoDtoDtoList = records.stream().map((item) -> {

            // 新创内部元素
            InfoDto infoDto = new InfoDto();

            // 普通值赋值
            BeanUtils.copyProperties(item,infoDto);

            // 通知详情赋值
            Long itemId = item.getId();

            infoDto.setName(item.getName());
            infoDto.setText(item.getText());

            return infoDto;
        }).collect(Collectors.toList());

        // 完成dishDtoPage的results的内容封装
        infoDtoPage.setRecords(infoDtoDtoList);

        return R.success(infoDtoPage);
    }
}
