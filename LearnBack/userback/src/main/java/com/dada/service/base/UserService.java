package com.dada.service.base;

import com.alibaba.fastjson.JSON;
import com.dada.entity.vo.UserInfoVO;
import com.dada.service.redis.RedisService;
import com.dada.service.sys.SysUserService;
import com.dada.util.EhcacheUtil;
import constant.EhcacheContant;
import constant.RedisContant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class UserService {
    @Autowired
    private RedisService redisService;

    @Autowired
    private SysUserService sysUserService;


    /**获取当前登录用户信息*/
    public UserInfoVO getCurrentUser() {

        /*获取当前用户名称*/
        String userName = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        /*先从Ehcache中获取*/
        Object object = EhcacheUtil.get(EhcacheContant.USER_KEY+userName);
        if (object != null) {
            log.info("从Ehcache中获取用户信息:{}获取时间:{}", object, new Date());
            return (UserInfoVO) object;
        }

        /*再从redis中获取*/
        String userJson = redisService.get(RedisContant.USER_KEY + userName);
        if (StringUtils.isNotBlank(userJson)) {
            log.info("从Redis中获取用户信息:{}获取时间:{}", userJson, new Date());
            // 放到Ehcache缓存中
            UserInfoVO userInfoVO = JSON.parseObject(userJson, UserInfoVO.class);
            EhcacheUtil.put(EhcacheContant.DEFAULT_CACHE_NAME,EhcacheContant.USER_KEY+userName,userInfoVO);
            log.info("Ehcache缓存用户信息:{}缓存时间:{}", userInfoVO, new Date());
            return JSON.parseObject(userJson, UserInfoVO.class);
        }

        //获取不到查询数据库
        UserInfoVO userInfo = sysUserService.getUserInfo(userName);
        log.info("从Mysql中获取用户信息:{}获取时间:{}", userInfo, new Date());

        // 放到redis缓存中
        redisService.setEx(RedisContant.USER_KEY + userInfo.getUsername(), JSON.toJSONString(userInfo), 28800, TimeUnit.SECONDS);
        log.info("redis缓存用户信息:{}缓存时间:{}", userInfo, new Date());

        // 放到Ehcache缓存中
        EhcacheUtil.put(EhcacheContant.DEFAULT_CACHE_NAME,EhcacheContant.USER_KEY+userName,userInfo);
        log.info("Ehcache缓存用户信息:{}缓存时间:{}", userInfo, new Date());


        return userInfo;
    }


    /**
     * 获取当前用户名称
     */
    public String getCurrentUserName() {
        /*获取当前用户名称*/
        String userName = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userName;
    }

}
