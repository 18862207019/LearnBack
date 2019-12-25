package com.dada.config.rocketmq.transactional.producer;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;

/**
 * 配置消息生产者
 *
 * @author HUPD
 */
@EnableBinding({ProducerQueueConfig.MySource.class})
public class ProducerQueueConfig {
  public interface MySource {
  /**
   * 发送消息 到OUTPUT队列
   * @return 消息通道
   */
    @Output(Source.OUTPUT)
    MessageChannel output();
  }
}
