package com.yzh.fv.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yzh.fv.common.BaseContext;
import com.yzh.fv.common.CustomException;
import com.yzh.fv.common.R;
import com.yzh.fv.dto.VoucherDto;
import com.yzh.fv.entity.*;
import com.yzh.fv.mapper.VoucherMapper;
import com.yzh.fv.mapper.VoucherUserMapper;
import com.yzh.fv.service.VoucherServer;
import com.yzh.fv.service.VoucherUserServer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 优惠券类
 *
 * @author : xiao
 * @since ： 2023/5/13 15:12
 */
@RestController
@Slf4j
@RequestMapping("/voucher")
public class VoucherController {
    @Resource
    private VoucherServer voucherServer;

    @Resource
    private VoucherMapper voucherMapper;

    @Resource
    private VoucherUserServer voucherUserServer;

    @Autowired
    private SqlSession sqlSession;

    @Resource
    private VoucherUserMapper voucherUserMapper;


    @PostMapping()
    public R<String> addVoucher(@RequestBody Voucher voucher){
        log.info(voucher.toString());
        voucherServer.save(voucher);
        return R.success("新增优惠券成功！");
    }


    /**
     * 后台分页查询
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
        //分页构造器
        Page<Voucher> pageInfo = new Page<>(page, pageSize);
        //条件构造器
        LambdaQueryWrapper<Voucher> voucherQuery = new LambdaQueryWrapper<>();
        voucherQuery
                .like(StringUtils.isNotEmpty(name), Voucher::getName, name)
                .orderByDesc(Voucher::getCreateTime);
        //执行查询
        voucherServer.page(pageInfo, voucherQuery);
        return R.success(pageInfo);
    }

    /**
     * 根据id删除优惠券
     * @param ids
     * @return
     */
    @DeleteMapping()
    public R<String> deleteVoucher(@RequestParam List<Long> ids) {
        //先查询是否存在起售
        LambdaQueryWrapper<Voucher> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .in(Voucher::getId, ids)
                .eq(Voucher::getStatus, 1);
        if (voucherServer.count(queryWrapper) > 0) {
            throw new CustomException("该优惠券正在启用中，不可删除！");
        }
        //删除
        voucherServer.removeByIds(ids);
        return R.success("优惠券删除成功！");
    }

    /**
     * 优惠券停用
     * 前台应提前告知客户停用时间
     * @param ids
     * @return
     */
    @PostMapping("/status/0")
    public R<String> statusStop(@RequestParam List<Long> ids) {
        //根据输入的ids，进行停用
        LambdaQueryWrapper<Voucher> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .in(Voucher::getId, ids)
                .eq(Voucher::getStatus, 1);
        int count = voucherServer.count(queryWrapper);
        if (count > 0) {
            for (Long id : ids) {
                Voucher voucher = voucherServer.getById(id);
                voucher.setStatus(0);
                voucherServer.updateById(voucher);
            }
        }
        UpdateWrapper<VoucherUser> userUpdateWrapper = new UpdateWrapper<VoucherUser>();
        userUpdateWrapper.set("voucher_id",0);
        userUpdateWrapper.set("status",0);
        voucherUserMapper.update(null, userUpdateWrapper);
        return R.success("优惠券已停用！");
    }

    /**
     * 优惠券启用
     *
     * @param ids
     * @return
     */
    @PostMapping("/status/1")
    public R<String> statusStart(@RequestParam List<Long> ids) {
        //根据输入的ids，进行启用
        LambdaQueryWrapper<Voucher> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .in(Voucher::getId, ids)
                .eq(Voucher::getStatus, 0);
        int count = voucherServer.count(queryWrapper);
        //TODO 如果启用优惠券，每个用户的voucher_id都会加上，如果停用，则变为null
        if (count > 0) {
            for (Long id : ids) {
                Voucher voucher = voucherServer.getById(id);
                voucher.setStatus(1);
                voucher.setUpdateTime(LocalDateTime.now());
                voucherServer.updateById(voucher);
            }
            // 启用优惠劵后
            // 获取更新最近的一条
        }

        LambdaQueryWrapper<Voucher>  queryWrapperV  =  new  LambdaQueryWrapper<>();
        queryWrapperV.eq(Voucher::getStatus,  1)
                .orderByDesc(Voucher::getUpdateTime)
                .last("limit  1");
        Voucher  one  =  voucherMapper.selectOne(queryWrapperV);
        UpdateWrapper<VoucherUser> userUpdateWrapper = new UpdateWrapper<VoucherUser>();
        userUpdateWrapper.set("voucher_id",one.getId());
        userUpdateWrapper.set("status",1);
        voucherUserMapper.update(null, userUpdateWrapper);

        return R.success("优惠券已启用！");

    }

    /**
     *  以下是客户端的优惠券功能
     */
    @GetMapping("/getVoucher")
    public R<List<VoucherDto>> getVou(){
        List<VoucherDto> voucherDtoList = null;
        log.info("查看优惠劵..");
        LambdaQueryWrapper<VoucherUser> userWrapper = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<Voucher> voucherWrapper = new LambdaQueryWrapper<>();
        userWrapper.eq(VoucherUser::getUserId,BaseContext.getCurrentId());
        VoucherUser user = voucherUserServer.getOne(userWrapper);
        if (user.getVoucherId() != 0 && user.getStatus() != 0){
            // 有优惠劵且没有使用
            voucherWrapper.eq(Voucher::getId,user.getVoucherId());
            //Voucher voucher = voucherServer.getOne(voucherWrapper);// 得到优惠劵
            List<Voucher> voucherList = voucherServer.list(voucherWrapper);
            voucherDtoList = voucherList.stream().map((item) -> {
                VoucherDto voucherDto = new VoucherDto();
                // 拷贝忽略records
                BeanUtils.copyProperties(item,voucherDto);
                Long id = item.getId();
                Voucher voucher = voucherServer.getById(id);// 得到优惠劵
                voucherDto.setId(id);
                voucherDto.setName(voucher.getName());
                voucherDto.setPrice(voucher.getPrice());
                voucherDto.setImage(voucher.getImage());
                voucherDto.setDescription(voucher.getDescription());
                return voucherDto;
            }).collect(Collectors.toList());
            return R.success(voucherDtoList);
        }
        return R.error("暂无优惠劵");
    }

}
