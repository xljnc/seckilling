package com.wt.seckilling.service.impl;

import ai.ii.common.service.BaseServiceImpl;
import com.wt.seckilling.entity.Order;
import com.wt.seckilling.mapper.OrderMapper;
import com.wt.seckilling.service.OrderService;
import org.springframework.stereotype.Service;

/**
 * @author: qiyu
 * @date: 2019/12/20 10:26
 * @description:
 */
@Service
public class OrderServiceImpl extends BaseServiceImpl<OrderMapper, Order> implements OrderService {
}
