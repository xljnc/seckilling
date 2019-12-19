package com.wt.seckilling.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: qiyu
 * @date: 2019/12/19 17:23
 * @description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product implements Serializable {
    private static final long serialVersionUID = -2077456064712599202L;

    @TableId(type = IdType.AUTO)
    private Long productId;

    /**
     * 库存
     **/
    private Integer stock;

    /**
     * 创建时间
     **/
    private Date createTime;

    /**
     * 更新时间
     **/
    private Date modifyTime;


    /**
     * 乐观锁版本
     **/
    private Long version;
}
