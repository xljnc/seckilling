package com.wt.seckilling.biz;

import com.wt.seckilling.dto.SeckillingSubmitDTO;

/**
 * @author: qiyu
 * @date: 2019/12/19 17:15
 * @description:
 */
public interface SeckillingBiz {

    String getSeckillingUrl(Long productId, Long customerId);

    void submitSeckilling(Long productId, Long customerId, Integer randomValue, SeckillingSubmitDTO submitDTO);
}
