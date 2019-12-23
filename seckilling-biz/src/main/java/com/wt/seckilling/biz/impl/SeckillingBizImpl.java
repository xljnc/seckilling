package com.wt.seckilling.biz.impl;

import ai.ii.common.redis.util.RedisUtil;
import ai.ii.common.util.UUIDUtil;
import com.wt.seckilling.aop.SemaphoreServiceLimit;
import com.wt.seckilling.biz.OrderBiz;
import com.wt.seckilling.biz.ProductBiz;
import com.wt.seckilling.biz.SeckillingBiz;
import com.wt.seckilling.dto.OrderCreateDTO;
import com.wt.seckilling.dto.SeckillingSubmitDTO;
import com.wt.seckilling.exception.SeckillingRuntimeException;
import com.wt.seckilling.mq.SeckillingMQProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

/**
 * @author: qiyu
 * @date: 2019/12/19 17:15
 * @description:
 */
@Service
@Slf4j
public class SeckillingBizImpl implements SeckillingBiz {

    private Random random = new Random();

    @Autowired
    private RedisUtil redisUtil;

    private String redisKeyPrefix = "SECKILLING::";

    @Value("${url.check:true}")
    private Boolean urlCheck;

    @Autowired
    private ProductBiz productBiz;

    @Autowired
    private SeckillingMQProducer seckillingMQProducer;

    @Autowired
    private OrderBiz orderBiz;

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
        productBiz.decreaseRedisStock(productId, 1, customerId);
        OrderCreateDTO createDTO = OrderCreateDTO.builder()
                .orderCode(UUIDUtil.getUUID())
                .customerId(customerId)
                .productId(productId)
                .orderDetail(submitDTO.getOrderDetail()).build();
        try {
            seckillingMQProducer.sendObject(createDTO, "order");
        } catch (Exception e) {
            log.error("发送MQ订单消息失败", e);
            throw new SeckillingRuntimeException(9008, "发送MQ订单消息失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void handleMqSeckillingMessage(OrderCreateDTO createDTO) {
        orderBiz.createOrder(createDTO);
        productBiz.decreaseDatabaseStock(createDTO.getProductId(), 1);
    }

    private boolean urlCheck(Long productId, Long customerId, Integer randomValue) {
        String redisKey = new StringBuilder(redisKeyPrefix).append(productId).append("::").append(customerId).toString();
        Integer redisValue = (Integer) redisUtil.getObjectValue(redisKey);
        return redisValue.equals(randomValue);
    }
}
