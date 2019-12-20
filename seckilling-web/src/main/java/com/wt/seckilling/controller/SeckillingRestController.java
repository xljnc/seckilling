package com.wt.seckilling.controller;

import ai.ii.common.dto.RestResult;
import com.wt.seckilling.biz.SeckillingBiz;
import com.wt.seckilling.api.SeckillingApi;
import com.wt.seckilling.dto.SeckillingSubmitDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: qiyu
 * @date: 2019/12/19 17:11
 * @description:
 */
@RestController
public class SeckillingRestController implements SeckillingApi {

    @Autowired
    private SeckillingBiz seckillingBiz;

    @Override
    public RestResult<String> getSeckillingUrl(Long productId, Long customerId) {
        String url = seckillingBiz.getSeckillingUrl(productId, customerId);
        return new RestResult<>(RestResult.DEFAULT_SUCCESS_CODE, RestResult.DEFAULT_SUCCESS_MSG, url);
    }

    @Override
    public RestResult<Integer> submitSeckilling(Long productId, Long customerId, Integer randomValue, SeckillingSubmitDTO submitDTO) {
        seckillingBiz.submitSeckilling(productId, customerId, randomValue, submitDTO);
        return new RestResult<>(RestResult.DEFAULT_SUCCESS_CODE, RestResult.DEFAULT_SUCCESS_MSG, null);
    }
}
