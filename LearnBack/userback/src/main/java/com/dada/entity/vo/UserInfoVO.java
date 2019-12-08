package com.dada.entity.vo;

import com.google.common.collect.Sets;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * 用户信息
 */
@Data
public class UserInfoVO implements Serializable {

    private Integer id;

    /**
     * 账号
     */
    private String username;

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


    private Set<String> roles = Sets.newHashSet();

    private Set<MenuVO> menus = Sets.newHashSet();

    private Set<ButtonVO> buttons = Sets.newHashSet();

}
