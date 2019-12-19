package com.wt.seckilling.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: qiyu
 * @date: 2019/12/19 17:20
 * @description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("orders")
public class Order implements Serializable {

    private static final long serialVersionUID = -7227094251055852697L;

    @TableId(type = IdType.AUTO)
    private Long orderId;

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

    /**
     * 创建时间
     **/
    private Date createTime;

    /**
     * 更新时间
     **/
    private Date modifyTime;

}
