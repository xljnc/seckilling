package com.wt.seckilling.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author: qiyu
 * @date: 2019/12/19 21:19
 * @description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderCreateDTO implements Serializable {
    private static final long serialVersionUID = 3648726857641213148L;

    /**
     * 订单号
     **/
    private String orderCode;

    /**
     * 用户ID
     **/
    private Long customerId;

    /**
     * 商品ID
     **/
    private Long productId;

    /**
     * 订单详情
     **/
    private String orderDetail;
}
