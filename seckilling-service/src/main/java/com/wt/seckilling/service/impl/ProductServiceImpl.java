package com.wt.seckilling.service.impl;

import ai.ii.common.service.BaseServiceImpl;
import com.wt.seckilling.entity.Product;
import com.wt.seckilling.mapper.ProductMapper;
import com.wt.seckilling.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: qiyu
 * @date: 2019/12/19 20:56
 * @description:
 */
@Service
public class ProductServiceImpl extends BaseServiceImpl<ProductMapper, Product> implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public boolean decreaseDatabaseStock(Product product, int num) {
        int result = productMapper.decreaseDatabaseStock(product.getProductId(), product.getVersion(), num);
        return result > 0;
    }
}
