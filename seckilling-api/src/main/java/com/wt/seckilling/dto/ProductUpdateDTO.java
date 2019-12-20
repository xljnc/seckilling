package com.wt.seckilling.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author: qiyu
 * @date: 2019/12/19 21:11
 * @description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductUpdateDTO implements Serializable {
    private static final long serialVersionUID = 1222804915364812827L;

    @NotNull(message = "商品ID不能为空")
    private Long productId;

    /**
     * 库存
     **/
    @Range(min = 0L, message = "库存必须大于0")
    private Integer stock;
}
