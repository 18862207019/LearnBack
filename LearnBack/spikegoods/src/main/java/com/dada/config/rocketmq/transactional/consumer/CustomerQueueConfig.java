package com.dada.config.rocketmq.transactional.consumer;


import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.SubscribableChannel;

/**
 * RocketMQ消费者队列+死信队列
 * @author HUPD
 */
@EnableBinding({ Source.class, CustomerQueueConfig.MySink.class })
public class CustomerQueueConfig {
    public interface MySink {
        /**
         * 消费者队列 input
         * @return 订阅的消息通道input
         */
        @Input(Sink.INPUT)
        SubscribableChannel input();

        /**
         * 死信队列
         * @return 订阅的消息通道inputDlq
         */
        @Input("inputDlq")
        SubscribableChannel inputDlq();
    }
}
