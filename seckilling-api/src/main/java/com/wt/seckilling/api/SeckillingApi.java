package com.wt.seckilling.api;

import ai.ii.common.dto.RestResult;
import com.wt.seckilling.dto.SeckillingSubmitDTO;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author: qiyu
 * @date: 2019/12/19 16:32
 * @description:
 */
@RequestMapping("/seckilling")
public interface SeckillingApi {

    @GetMapping("/url/{productId}/{customerId}")
    RestResult<String> getSeckillingUrl(@PathVariable("productId") Long productId, @PathVariable("customerId") Long customerId);

    @PostMapping("/{productId}/{customerId}/{randomValue}/submit")
    RestResult<Integer> submitSeckilling(@PathVariable("productId") Long productId, @PathVariable("customerId") Long customerId, @PathVariable("customerId") String randomValue, @RequestBody @Valid SeckillingSubmitDTO submitDTO);

}
