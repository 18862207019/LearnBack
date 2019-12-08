package com.dada.config;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.dada.entity.sys.SysUser;
import com.dada.entity.vo.UserInfoVO;
import com.dada.feign.user.UserRpc;
import com.dada.service.RedisService;
import com.google.common.collect.Lists;
import constant.RedisContant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private UserRpc userRpc;

  @Autowired
  private RedisService redisService;


  /**
   * 功能描述:  用户登录 以及设置用户权限
   */
  @Override
  public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

    /*通过用户名称获取用户*/
    SysUser sysUser = userRpc.getByName(userName).getData();

    /*放置权限*/
    List<GrantedAuthority> grantedAuthorities = Lists.newArrayList();

    if (sysUser != null) {
      UserInfoVO userInfoVO = userRpc.getUserInfo(userName).getData();

      /*数据存入redis*/
      redisService.setEx(RedisContant.USER_KEY + userInfoVO.getUsername(),
          JSON.toJSONString(userInfoVO),
          28800,
          TimeUnit.SECONDS);

      userInfoVO.getButtons().forEach(buttonVO -> {
        /*拼接ROLE_设置拥有的权限*/
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_" + buttonVO.getResources());
        grantedAuthorities.add(grantedAuthority);
      });
    }
    return new User(Objects.requireNonNull(sysUser).getUsername(), sysUser.getPassword(), grantedAuthorities);
  }
}
