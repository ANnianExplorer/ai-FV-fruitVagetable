package com.yzh.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yzh.reggie.common.CustomException;
import com.yzh.reggie.common.R;
import com.yzh.reggie.entity.Voucher;
import com.yzh.reggie.service.VoucherServer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

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

    @PostMapping()
    public R<String> addVoucher(@RequestBody Voucher voucher){
        log.info(voucher.toString());
        voucherServer.save(voucher);
        return R.success("新增优惠券成功！");
    }


    /**
     * 分页查询
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
        return R.success("优惠券已停用！");
    }

    /**
     * 优惠券停用
     *
     * @param ids
     * @return
     */
    @PostMapping("/status/1")
    public R<String> statusStart(@RequestParam List<Long> ids) {
        //根据输入的ids，进行停用
        LambdaQueryWrapper<Voucher> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .in(Voucher::getId, ids)
                .eq(Voucher::getStatus, 0);
        int count = voucherServer.count(queryWrapper);
        if (count > 0) {
            for (Long id : ids) {
                Voucher voucher = voucherServer.getById(id);
                voucher.setStatus(1);
                voucherServer.updateById(voucher);
            }
        }
        return R.success("优惠券已启用！");
    }
}
