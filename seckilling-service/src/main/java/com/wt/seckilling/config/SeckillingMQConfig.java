package com.wt.seckilling.config;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * @auther: 埼玉
 * @date: 2019/9/10 21:03
 * @description: MQ配置类
 */
@EnableBinding({SeckillingMQConfig.SeckillingSink.class, SeckillingMQConfig.SeckillingSource.class})
public class SeckillingMQConfig {

    public static final String ORDER_INPUT = "seckillingOrderInput";

    public interface SeckillingSink {

        @Input(ORDER_INPUT)
        SubscribableChannel seckillingOrderInput();
    }


    public static final String SECKILLING_OUTPUT = "seckillingOutput";

    public interface SeckillingSource {
        @Output(SECKILLING_OUTPUT)
        MessageChannel seckillingOutput();
    }


}
