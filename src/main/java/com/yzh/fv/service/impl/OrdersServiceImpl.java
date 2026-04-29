package com.yzh.fv.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.yzh.fv.common.BaseContext;
import com.yzh.fv.common.CustomException;
import com.yzh.fv.entity.*;
import com.yzh.fv.mapper.OrderMapper;
import com.yzh.fv.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrdersServiceImpl extends ServiceImpl<OrderMapper, Orders> implements OrdersService {

    @Resource
    private OrderDetailService orderDetailService;
    @Resource
    private ShoppingCartService shoppingCartService;
    @Resource
    private UserService userService;
    @Resource
    private AddressBookService addressBookService;
    @Resource
    private VoucherServer voucherService;
    
    @Resource
    private DishService dishService;
    
    @Resource
    private VoucherUserServer voucherUserService;
    
    @Resource
    private OrderVoucherService orderVoucherService;
    /**
     * 下单
     * 仅处理订单和订单明细的存储，优惠券相关逻辑已在点击使用优惠券时处理
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void submit(Orders orders) {
        //获得当前用户id
        Long userId = BaseContext.getCurrentId();
        log.info("从ThreadLocal获取当前用户ID: {}", userId);
        
        if (userId == null) {
            log.error("无法从ThreadLocal获取用户ID，用户未登录或登录状态失效");
            throw new CustomException("用户未登录，请先登录");
        }

        //查询当前用户的购物车数据
        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShoppingCart::getUserId, userId);
        List<ShoppingCart> shoppingCarts = shoppingCartService.list(wrapper);

        if (shoppingCarts == null || shoppingCarts.size() == 0) {
            throw new CustomException("购物车为空，不能下单");
        }

        //查询用户数据
        log.info("准备查询用户数据，用户ID: {}", userId);
        User user = userService.getById(userId);
        
        // 获取用户信息
        String userName = "";
        if (user == null) {
            // 检查请求中是否包含登录类型标识
            String loginType = (String) ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getAttribute("loginType");
            // 如果是管理员登录，使用默认用户名
            if ("employee".equals(loginType)) {
                userName = "管理员";
                log.warn("用户不存在，但为管理员登录，使用默认用户名：{}", userName);
            } else {
                throw new RuntimeException("用户不存在");
            }
        } else {
            userName = user.getName();
            log.info("查询到用户信息：{}, {}", userName, user.getPhone());
        }

        //查询地址数据
        Long addressBookId = orders.getAddressBookId();
        AddressBook addressBook = addressBookService.getById(addressBookId);
        if (addressBook == null) {
            throw new CustomException("用户地址信息有误，不能下单");
        }

        // 计算商品总价
        long orderId = IdWorker.getId();//订单号
        AtomicInteger amount = new AtomicInteger(0);

        List<OrderDetail> orderDetails = shoppingCarts.stream().map((item) -> {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(orderId);
            orderDetail.setNumber(item.getNumber());
            orderDetail.setDishFlavor(item.getDishFlavor());
            orderDetail.setDishId(item.getDishId());
            orderDetail.setSetmealId(item.getSetmealId());
            orderDetail.setName(item.getName());
            orderDetail.setImage(item.getImage());
            orderDetail.setAmount(item.getAmount());
            amount.addAndGet(item.getAmount().multiply(new BigDecimal(item.getNumber())).intValue());
            return orderDetail;
        }).collect(Collectors.toList());

        // 处理优惠券相关逻辑（简化版，不再重新验证）
        Long voucherId = orders.getVoucherId();
        if (voucherId != null) {
            // 直接设置订单中已有的优惠信息
            // 注意：优惠券状态更新和验证已在useCoupon接口中处理
            // 这里只需要关联订单ID到优惠券使用记录
            LambdaQueryWrapper<VoucherUser> voucherUserWrapper = new LambdaQueryWrapper<>();
            voucherUserWrapper.eq(VoucherUser::getUserId, userId);
            voucherUserWrapper.eq(VoucherUser::getVoucherId, voucherId);
            voucherUserWrapper.eq(VoucherUser::getStatus, 1); // 1表示已使用
            VoucherUser voucherUser = voucherUserService.getOne(voucherUserWrapper);
            
            // 如果优惠券使用记录存在但订单ID为空，则更新订单ID
            if (voucherUser != null && voucherUser.getOrderId() == null) {
                UpdateWrapper<VoucherUser> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("id", voucherUser.getId());
                updateWrapper.set("order_id", orderId);
                voucherUserService.update(updateWrapper);
            }

            // 设置订单的原始金额和优惠金额
            if (orders.getOriginalAmount() == null) {
                // 如果前端没有传入原始金额，使用当前计算的金额加上优惠金额
                // 不对，
                orders.setOriginalAmount(new BigDecimal(amount.get()).add(orders.getVoucherAmount() != null ? orders.getVoucherAmount() : BigDecimal.ZERO));
            }
            
            // 记录优惠券使用记录到order_voucher表
            OrderVoucher orderVoucher = new OrderVoucher();
            orderVoucher.setOrderId(orderId);
            orderVoucher.setVoucherId(voucherId);
            orderVoucher.setUserId(userId);
            // 优惠金额已在前端传入，直接使用
            BigDecimal voucherAmount = orders.getVoucherAmount() != null ? orders.getVoucherAmount() : BigDecimal.ZERO;
            orderVoucher.setDiscountAmount(voucherAmount);
            orderVoucher.setCreateTime(LocalDateTime.now());
            orderVoucherService.save(orderVoucher);
            
            // 如果前端未传递amount字段，则按原逻辑计算，否则直接使用前端传递的值
            if (orders.getAmount() == null) {
                // 设置订单最终实付金额（原始金额减去优惠金额）
                BigDecimal finalAmount = orders.getOriginalAmount().subtract(voucherAmount);
                if (finalAmount.compareTo(BigDecimal.ZERO) < 0) {
                    orders.setAmount(BigDecimal.ZERO);
                } else {
                    orders.setAmount(finalAmount);
                }
            }
        } else {
            // 没有使用优惠券，直接设置总价
            if (orders.getAmount() == null) {
                orders.setAmount(new BigDecimal(amount.get()));
            }
            if (orders.getOriginalAmount() == null) {
                orders.setOriginalAmount(new BigDecimal(amount.get()));
            }
            if (orders.getVoucherAmount() == null) {
                orders.setVoucherAmount(BigDecimal.ZERO);
            }
        }

        // 设置订单基本信息
        orders.setId(orderId);
        orders.setOrderTime(LocalDateTime.now());
        orders.setCheckoutTime(LocalDateTime.now());
        orders.setStatus(2);
        orders.setVoucherId(voucherId);
        orders.setUserId(userId);
        orders.setNumber(String.valueOf(orderId));
        orders.setUserName(userName);
        orders.setConsignee(addressBook.getConsignee());
        orders.setPhone(addressBook.getPhone());
        orders.setAddress((addressBook.getProvinceName() == null ? "" : addressBook.getProvinceName())
                + (addressBook.getCityName() == null ? "" : addressBook.getCityName())
                + (addressBook.getDistrictName() == null ? "" : addressBook.getDistrictName())
                + (addressBook.getDetail() == null ? "" : addressBook.getDetail()));
        
        try {
            //向订单表插入数据，一条数据
            this.save(orders);

            //向订单明细表插入数据，多条数据
            orderDetailService.saveBatch(orderDetails);

            //清空购物车数据
            shoppingCartService.remove(wrapper);
            
            // 更新菜品销量
            for (ShoppingCart cart : shoppingCarts) {
                if (cart.getDishId() != null) {
                    Dish dish = dishService.getById(cart.getDishId());
                    if (dish != null) {
                        // 使用乐观锁更新销量
                        UpdateWrapper<Dish> dishUpdateWrapper = new UpdateWrapper<>();
                        dishUpdateWrapper.eq("id", dish.getId());
                        dishUpdateWrapper.setSql("sales = sales + " + cart.getNumber());
                        dishService.update(dishUpdateWrapper);
                    }
                }
            }
            
        } catch (Exception e) {
            log.error("订单提交失败: {}", e.getMessage());
            // 事务会自动回滚，这里记录日志
            throw new CustomException("订单提交失败，请重试");
        }
    }
}