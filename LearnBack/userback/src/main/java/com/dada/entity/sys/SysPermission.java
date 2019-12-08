package com.dada.entity.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName(value = "sys_permission")
public class SysPermission implements Serializable {
    /**
     * 主键
     */
     @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 上级资源ID
     */
    @TableField(value = "parent_id")
    private String parentId;

    /**
     * 资源编码
     */
    @TableField(value = "resources")
    private String resources;

    /**
     * 资源名称
     */
    @TableField(value = "title")
    private String title;

    /**
     * 资源级别
     */
    @TableField(value = "level")
    private Integer level;

    /**
     * 排序
     */
    @TableField(value = "sort_no")
    private Integer sortNo;

    /**
     * 资源图标
     */
    @TableField(value = "icon")
    private String icon;

    /**
     * 类型 menu、button
     */
    @TableField(value = "type")
    private String type;

    /**
     * 备注
     */
    @TableField(value = "remarks")
    private String remarks;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Date updateTime;

    private static final long serialVersionUID = 1L;

}