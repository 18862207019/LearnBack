/**
 * 版权所有(C)，上海勾芒信息科技，2017，所有权利保留。
 * 
 * 项目名：	wxmall
 * 文件名：	SysEvent.java
 * 模块说明：	
 * 修改历史：
 * 2017年9月12日 - Debenson - 创建。
 */
package com.dada.config.rocketmq.message;

import java.io.Serializable;

/**
 * 系统消息
 * 
 * @author HUPD
 */
public interface SysEvent extends Serializable {

  /**
   * 事件类型
   * 
   * @return
   */
   SysEventType getType();

  /**
   * 事件相关数据
   * 
   * @return
   */
   Object getData();

}
