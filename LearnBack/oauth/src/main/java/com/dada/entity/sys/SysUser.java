package com.dada.entity.sys;


import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@Data
public class SysUser implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 账号
     */
    private String username;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 性别：0是男 1是女
     */
    private String sex;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 状态
     */
    private String flag;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}