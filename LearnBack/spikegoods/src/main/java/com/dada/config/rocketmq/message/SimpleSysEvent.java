/**
 * 版权所有(C)，上海勾芒信息科技，2017，所有权利保留。
 * 
 * 项目名：	wxmall
 * 文件名：	SimpleSysEvent.java
 * 模块说明：	
 * 修改历史：
 * 2017年9月12日 - Debenson - 创建。
 */
package com.dada.config.rocketmq.message;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Debenson
 * @since 0.1
 */
@Data
@AllArgsConstructor
public class SimpleSysEvent implements SysEvent {
  private static final long serialVersionUID = 4851981580600314392L;

  private SysEventType type;
  private Object data;

// TODO EXIMPLE
//  /**
//   * 站内信--活动通知
//   *
//   * @param activityId
//   * @return
//   */
//  public static SimpleSysEvent letterActivityCreateSender(String activityId) {
//    return new SimpleSysEvent(SysEventType.LETTER_ACTIVITY, activityId);
//  }
}
