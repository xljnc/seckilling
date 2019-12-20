package com.wt.seckilling.biz.impl;

import ai.ii.common.redis.util.RedisUtil;
import com.wt.seckilling.biz.SeckillingBiz;
import com.wt.seckilling.aop.SemaphoreServiceLimit;
import com.wt.seckilling.dto.SeckillingSubmitDTO;
import com.wt.seckilling.exception.SeckillingRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${url.check:true}")
    private Boolean urlCheck;

    @Override
    public String getSeckillingUrl(Long productId, Long customerId) {
        Integer randomValue = random.nextInt();
        String redisKey = new StringBuilder(redisKeyPrefix).append(productId).append("::").append(customerId).toString();
        redisUtil.setObjectValue(redisKey, randomValue);
        return new StringBuilder("/").append(productId).append("/")
                .append(customerId).append("/").append(randomValue).append("/submit").toString();
    }

    @Override
    @SemaphoreServiceLimit
    public void submitSeckilling(Long productId, Long customerId, Integer randomValue, SeckillingSubmitDTO submitDTO) {
        if (urlCheck) {
            boolean checkResult = urlCheck(productId, customerId, randomValue);
            if (!checkResult)
                throw new SeckillingRuntimeException(9001, "URL不合法");
        }
        

    }

    private boolean urlCheck(Long productId, Long customerId, Integer randomValue) {
        String redisKey = new StringBuilder(redisKeyPrefix).append(productId).append("::").append(customerId).toString();
        Integer redisValue = (Integer) redisUtil.getObjectValue(redisKey);
        return redisValue.equals(randomValue);
    }
}
