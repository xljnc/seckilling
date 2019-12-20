package com.wt.seckilling.mq;

import org.apache.rocketmq.common.message.MessageConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;

/**
 * @auther: 埼玉
 * @date: 2019/9/11 11:50
 * @description:
 */
@Component
public class SeckillingMQProducer {

    @Autowired
    private MessageChannel seckillingOutput;

    public <T> void sendObject(T msg) throws Exception {
        Message<T> message = MessageBuilder.withPayload(msg)
                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                .build();
        seckillingOutput.send(message);
    }

    public <T> void sendObject(T msg, String tag) throws Exception {
        Message<T> message = MessageBuilder.withPayload(msg)
                .setHeader(MessageConst.PROPERTY_TAGS, tag)
                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                .build();
        seckillingOutput.send(message);
    }

    public <T> void sendObject(T msg, String tag,Integer delayLevel) throws Exception {
        Message<T> message = MessageBuilder.withPayload(msg)
                .setHeader(MessageConst.PROPERTY_TAGS, tag)
                .setHeader(MessageConst.PROPERTY_DELAY_TIME_LEVEL, delayLevel)
                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                .build();
        seckillingOutput.send(message);
    }
}
