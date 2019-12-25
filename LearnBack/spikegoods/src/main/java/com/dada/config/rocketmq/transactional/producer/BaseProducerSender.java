package com.dada.config.rocketmq.transactional.producer;

import com.dada.config.rocketmq.message.SimpleSysEvent;

/**
 * 发送消息基础接口
 *
 * @author Hupd
 */
public interface BaseProducerSender {

  void sendStrMsg(String msg);

  void sendStrMsgAndTags(String msg, String tag);

  void sendWithSimpleSysEvent(SimpleSysEvent msg);

  void sendWithSimpleSysEventAndTags(SimpleSysEvent msg, String tag);
}