package com.wt.seckilling.api;

import ai.ii.common.dto.RestResult;
import com.wt.seckilling.dto.ProductCreateDTO;
import com.wt.seckilling.dto.ProductUpdateDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

/**
 * @author: qiyu
 * @date: 2019/12/19 20:39
 * @description:
 */
@RequestMapping("/product")
public interface ProductApi {

    @PostMapping("/create")
    RestResult<Integer> createProduct(@RequestBody @Valid ProductCreateDTO createDTO);

    @PostMapping("/update")
    RestResult<Integer> updateProduct(@RequestBody @Valid ProductUpdateDTO updateDTO);
}
