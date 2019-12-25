package com.dada.config.rocketmq.transactional.producer;

import com.dada.config.rocketmq.message.SimpleSysEvent;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 生产者发送消息适配器
 * @author hupd
 */
@Service
@Slf4j
public class ProducerSenderAdapter extends ProducerSenderByMessageChannel implements ProducerSender, InitializingBean {

  @Autowired
  private OutPutProducerSender outPutProducerSender;

  @Autowired
  private ProducerQueueConfig.MySource source;

  private static List<ProducerSenderEnum> SENDERTTYPES = Lists.newArrayList();

  static {
    SENDERTTYPES.add(ProducerSenderEnum.OUTPUT);
  }

  @Override
  public void sendStrMsg(String msg, ProducerSenderEnum senderEnum) {
    if (senderEnum.equals(ProducerSenderEnum.OUTPUT)) {
      outPutProducerSender.sendStrMsg(msg);
    }
  }

  @Override
  public void send(String msg, String tag, ProducerSenderEnum senderEnum) {
    if (senderEnum.equals(ProducerSenderEnum.OUTPUT)) {
      outPutProducerSender.sendStrMsgAndTags(msg, tag);
    }
  }

  @Override
  public void sendWithSimpleSysEvent(SimpleSysEvent msg, ProducerSenderEnum senderEnum) {
    if (senderEnum.equals(ProducerSenderEnum.OUTPUT)) {
      outPutProducerSender.sendWithSimpleSysEvent(msg);
    }
  }

  @Override
  public void sendWithSimpleSysEventAndTags(SimpleSysEvent msg, String tag, ProducerSenderEnum senderEnum) {
    if (senderEnum.equals(ProducerSenderEnum.OUTPUT)) {
      outPutProducerSender.sendWithSimpleSysEventAndTags(msg, tag);
    }
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    this.mySource=source;
  }
}
