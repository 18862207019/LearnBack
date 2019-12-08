package com.dada.entity.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName(value = "sys_role")
public class SysRole implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 角色编码
     */
    @TableField(value = "code")
    private String code;

    /**
     * 角色名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 角色描述
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

    /*************************扩展字段************************/
    /**
     * 是否已关联系统用户
     */
    @TableField(exist = false)
    private String isRelatedSysUser;

    /**
     * 是否已关联系统资源
     */
    @TableField(exist = false)
    private String isRelatedSysPermission;

    /**
     * 是否已关联微信账号
     */
    @TableField(exist = false)
    private String isRelatedWxAccount;

    private static final long serialVersionUID = 1L;

}