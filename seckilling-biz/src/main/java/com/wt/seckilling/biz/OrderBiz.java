package com.wt.seckilling.biz;

import com.wt.seckilling.dto.OrderCreateDTO;

/**
 * @author: qiyu
 * @date: 2019/12/20 10:20
 * @description:
 */
public interface OrderBiz {

    void createOrder(OrderCreateDTO createDTO);
}
