package com.dada.config.rocketmq.transactional.producer;

import com.dada.config.rocketmq.message.SimpleSysEvent;

import org.springframework.stereotype.Service;

/**
 * OutPut队列消息发送
 *
 * @author hupd
 */
@Service
public class OutPutProducerSender extends ProducerSenderByMessageChannel implements BaseProducerSender {

  @Override
  public void sendStrMsg(String msg) {
    this.sendStrMsg(msg, mySource.output());
  }

  @Override
  public void sendStrMsgAndTags(String msg, String tag) {
    this.sendStrMsgAndTags(msg, tag, mySource.output());
  }

  @Override
  public void sendWithSimpleSysEvent(SimpleSysEvent msg) {
    this.sendWithSimpleSysEvent(msg, mySource.output());
  }

  @Override
  public void sendWithSimpleSysEventAndTags(SimpleSysEvent msg, String tag) {
    this.sendWithSimpleSysEventAndTags(msg, tag, mySource.output());
  }
}
