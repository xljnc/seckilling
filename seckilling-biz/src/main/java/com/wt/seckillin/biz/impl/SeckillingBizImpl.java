package com.wt.seckillin.biz.impl;

import ai.ii.common.redis.util.RedisUtil;
import com.wt.seckillin.biz.SeckillingBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * @author: qiyu
 * @date: 2019/12/19 17:15
 * @description:
 */
@Service
public class SeckillingBizImpl implements SeckillingBiz {

    private Random random = new Random();

    @Autowired
    private RedisUtil redisUtil;

    private String redisKeyPrefix = "SECKILLING::";

    @Override
    public String getSeckillingUrl(Long productId, Long customerId) {
        Integer randomValue = random.nextInt();
        String redisKey = new StringBuilder(redisKeyPrefix).append(productId).append("::").append(customerId).toString();
        redisUtil.setObjectValue(redisKey, randomValue);
        return new StringBuilder("/").append(productId).append("/")
                .append(customerId).append("/").append(randomValue).append("/submit").toString();
    }
}
