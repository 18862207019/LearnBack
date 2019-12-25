package com.dada.config.rocketmq.transactional.producer;

import com.dada.config.rocketmq.message.SimpleSysEvent;
import lombok.Data;
import org.apache.rocketmq.common.message.MessageConst;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.MimeTypeUtils;

/**
 * 通过messageChannel发送消息
 * @author hupd
 */
@Data
public  class ProducerSenderByMessageChannel{

  ProducerQueueConfig.MySource mySource;
  /**
   * 发送字符消息内容
   * @param msg 字符串消息
   */
  public  void sendStrMsg(String msg,MessageChannel messageChannel) {
    messageChannel.send(MessageBuilder.withPayload(msg).build());
  }

  /**
   * 发送字符消息内容 携带tag匹配
   * @param msg
   * @param tag
   */
  public  void sendStrMsgAndTags(String msg, String tag, MessageChannel messageChannel) {
    messageChannel.send(MessageBuilder.withPayload(msg).build());
  }


  /**
   * 发送对象消息
   * @param msg 消息内容
   */
  public  void sendWithSimpleSysEvent(SimpleSysEvent msg, MessageChannel messageChannel) {
    Message message = MessageBuilder.withPayload(msg)
        .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
        .build();
    messageChannel.send(message);
  }

  /**
   * 发送对象消息 携带tag匹配
   * @param msg 消息内容
   * @param tag tag匹配
   */
  public  void sendWithSimpleSysEventAndTags(SimpleSysEvent msg, String tag, MessageChannel messageChannel) {
    Message message = MessageBuilder.withPayload(msg)
        .setHeader(MessageConst.PROPERTY_TAGS, tag)
        .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
        .build();
    messageChannel.send(message);
  }
}
