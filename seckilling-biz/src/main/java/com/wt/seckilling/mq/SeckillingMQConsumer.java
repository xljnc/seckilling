package com.wt.seckilling.mq;

import com.alibaba.fastjson.JSON;
import com.wt.seckilling.biz.SeckillingBiz;
import com.wt.seckilling.config.SeckillingMQConfig;
import com.wt.seckilling.dto.OrderCreateDTO;
import com.wt.seckilling.exception.MessageAlreadyConsumedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * @auther: 埼玉
 * @date: 2019/9/11 11:04
 * @description:
 */
@Component
@Slf4j
public class SeckillingMQConsumer {

    @Autowired
    private SeckillingBiz seckillingBiz;

    /**
     * 接收mq消息,类型为下单,tag为order
     **/
    @StreamListener(SeckillingMQConfig.ORDER_INPUT)
    public void consumeGrantNotifyMessage(@Payload OrderCreateDTO createDTO) throws Exception {
        try {
            seckillingBiz.handleMqSeckillingMessage(createDTO);
        } catch (MessageAlreadyConsumedException e) {
            log.error("MQ消息重复消费，参数:{}", JSON.toJSONString(createDTO), e);
        } catch (Exception e) {
            throw e;
        }

    }

}
