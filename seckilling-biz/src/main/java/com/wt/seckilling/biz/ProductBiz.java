package com.wt.seckilling.biz;

import com.wt.seckilling.dto.ProductCreateDTO;
import com.wt.seckilling.dto.ProductUpdateDTO;

/**
 * @author: qiyu
 * @date: 2019/12/19 20:55
 * @description:
 */
public interface ProductBiz {

    void createProduct(ProductCreateDTO createDTO);

    void updateProduct(ProductUpdateDTO updateDTO);

    void decreaseRedisStock(Long productId, int num);
}
