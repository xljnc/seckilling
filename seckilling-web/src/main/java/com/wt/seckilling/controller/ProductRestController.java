package com.wt.seckilling.controller;

import ai.ii.common.dto.RestResult;
import com.wt.seckilling.api.ProductApi;
import com.wt.seckilling.biz.ProductBiz;
import com.wt.seckilling.dto.ProductCreateDTO;
import com.wt.seckilling.dto.ProductUpdateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: qiyu
 * @date: 2019/12/19 20:49
 * @description:
 */
@RestController
public class ProductRestController implements ProductApi {

    @Autowired
    private ProductBiz productBiz;

    @Override
    public RestResult<Integer> createProduct(ProductCreateDTO createDTO) {
        productBiz.createProduct(createDTO);
        return new RestResult<>(RestResult.DEFAULT_SUCCESS_CODE, RestResult.DEFAULT_SUCCESS_MSG, null);
    }

    @Override
    public RestResult<Integer> updateProduct( ProductUpdateDTO updateDTO) {
        productBiz.updateProduct(updateDTO);
        return new RestResult<>(RestResult.DEFAULT_SUCCESS_CODE, RestResult.DEFAULT_SUCCESS_MSG, null);
    }
}
