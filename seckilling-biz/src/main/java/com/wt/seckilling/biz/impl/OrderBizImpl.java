package com.wt.seckilling.biz.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wt.seckilling.biz.OrderBiz;
import com.wt.seckilling.dto.OrderCreateDTO;
import com.wt.seckilling.entity.Order;
import com.wt.seckilling.exception.MessageAlreadyConsumedException;
import com.wt.seckilling.exception.SeckillingRuntimeException;
import com.wt.seckilling.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author: qiyu
 * @date: 2019/12/20 10:20
 * @description:
 */
@Service
public class OrderBizImpl implements OrderBiz {

    @Autowired
    private OrderService orderService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createOrder(OrderCreateDTO createDTO) {
        LambdaQueryWrapper<Order> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(Order::getOrderCode, createDTO.getOrderCode());
        Order currOrder = orderService.getOne(queryWrapper);
        if (currOrder != null)
            throw new MessageAlreadyConsumedException(9003, "订单已存在");
        Order order = JSON.parseObject(JSON.toJSONString(createDTO), Order.class);
        Date now = new Date();
        order.setCreateTime(now);
        order.setModifyTime(now);
        boolean result = orderService.save(order);
        if (!result)
            throw new SeckillingRuntimeException(9004, "订单创建失败");
    }
}
