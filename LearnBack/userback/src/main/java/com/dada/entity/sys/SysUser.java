package com.dada.entity.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName(value = "sys_user")
public class SysUser implements Serializable {
    /**
     * 主键
     */
     @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 账号
     */
    @TableField(value = "username")
    private String username;

    /**
     * 登录密码
     */
    @TableField(value = "password")
    private String password;

    /**
     * 昵称
     */
    @TableField(value = "nick_name")
    private String nickName;

    /**
     * 性别：0是男 1是女
     */
    @TableField(value = "sex")
    private String sex;

    /**
     * 手机号码
     */
    @TableField(value = "phone")
    private String phone;

    /**
     * 邮箱
     */
    @TableField(value = "email")
    private String email;

    /**
     * 头像
     */
    @TableField(value = "avatar")
    private String avatar;

    /**
     * 状态
     */
    @TableField(value = "flag")
    private String flag;

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