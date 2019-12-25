package com.dada.config.rocketmq.transactional.producer;

import com.dada.config.rocketmq.message.SimpleSysEvent;

/**
 * @author hupd
 */
public interface ProducerSender {

  void sendStrMsg(String msg,ProducerSenderEnum senderEnum);

  void send(String msg, String tag,ProducerSenderEnum senderEnum);

  void sendWithSimpleSysEvent(SimpleSysEvent msg,ProducerSenderEnum senderEnum);

  void sendWithSimpleSysEventAndTags(SimpleSysEvent msg, String tag,ProducerSenderEnum senderEnum);
}
