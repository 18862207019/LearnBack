package com.dada.feign.rpc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import util.result.Result;

/**
 * 提供远程调用接口（调用方如下）
 *  1,userback(模块)->com.dada.feign.rpc.OauthRpc(类)
 */
@RestController
@RequestMapping
public class Oauth2 {

    @Autowired
    @Qualifier("consumerTokenServices")
    private ConsumerTokenServices tokenServices;

    /**
     * 当前登陆用户信息
     */
    @GetMapping("/userme")
    public Authentication principal() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 注销登陆/退出
     */
    @DeleteMapping(value = "/remove_token", params = "access_token")
    public void removeToken(String access_token) {
        tokenServices.revokeToken(access_token);
    }

}
