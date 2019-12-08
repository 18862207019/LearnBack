package com.dada.entity.task;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dada.config.redis.param.ParamLock;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName(value = "sys_task")
public class SysTask implements Serializable {
  /** 主键id */
  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;

  /** 标题 */
  @ParamLock
  @TableField(value = "title")
  private String title;

  /** 状态(1未开始,2进行中,3结束) */
  @TableField(value = "state")
  private String state;

  /** 类型(完成,放弃) */
  @TableField(value = "type")
  private Integer type;

  /** 完成度(乘100后存入) */
  @TableField(value = "completion")
  private String completion;

  /** 文件名称 */
  @TableField(value = "file_name")
  private String fileName;

  /** 文件地址 */
  @TableField(value = "file_url")
  private String fileUrl;

  /** 图片地址 */
  @TableField(value = "image_url")
  private String imageUrl;

  /** 创建时间 */
  @JsonFormat(pattern = "yyyy-MM-dd")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @TableField(value = "c_time")
  private Date cTime;

  /** 修改时间 */
  @JsonFormat(pattern = "yyyy-MM-dd")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @TableField(value = "u_time")
  private Date uTime;

  /** 结束时间 */
  @JsonFormat(pattern = "yyyy-MM-dd")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @TableField(value = "end_time")
  private Date endTime;

  /** 用户id */
  private Integer createUserId;

  /** 解决方式 */
  private String solution;

  /** 当前完成情况备注 */
  private String remake;


  private static final long serialVersionUID = 1L;
}
