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
 * @date: 2019/12/19 20:43
 * @description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCreateDTO implements Serializable {

    /**
     * 库存
     **/
    @NotNull(message = "库存不能为空")
    @Range(min = 1L, message = "库存必须大于0")
    private Integer stock;

}
