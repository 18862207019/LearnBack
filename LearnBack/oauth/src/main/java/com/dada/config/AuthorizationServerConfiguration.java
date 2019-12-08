package com.dada.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.sql.DataSource;

/**
 * @Author HUPD
 * 认证服务器
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

  /**
   * 数据源
   */
  private final DataSource dataSource;

  /**
   * 认证管理
   */
  private final AuthenticationManager authenticationManager;

  /**
   * redis连接
   */
  private final RedisConnectionFactory redisConnectionFactory;

  /**
   * 用户信息
   */
  private final UserDetailsService userDetailsService;

  /**
   * 构造器方式注入bean
   *
   * @param authenticationManager  认证管理
   * @param userDetailsService     用户信息
   * @param dataSource             数据源
   * @param redisConnectionFactory redis数据源
   */
  public AuthorizationServerConfiguration(AuthenticationManager authenticationManager,
                                          UserDetailsService userDetailsService,
                                          @Qualifier("dataSource") DataSource dataSource,
                                          RedisConnectionFactory redisConnectionFactory) {
    this.authenticationManager = authenticationManager;
    this.userDetailsService = userDetailsService;
    this.dataSource = dataSource;
    this.redisConnectionFactory = redisConnectionFactory;
  }


  /**
   * JdbcClientDetailsService从数据库读取相应的配置  配置客户端详情信息
   * 客户端授权</br>
   * (1)绑定客户端jdbc<br/>
   * (2)设置Client信息<br/>
   * (3)采用 密码模式
   */
  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    clients.withClientDetails(new JdbcClientDetailsService(dataSource));
  }


  /**
   * OAuth2 token持久存储到redis中
   */
  @Bean
  public TokenStore tokenStore() {
    //token存储到redis中
    RedisTokenStore redisTokenStore = new RedisTokenStore(redisConnectionFactory);
    //解决同一username每次登陆access_token都相同的问题
    redisTokenStore.setAuthenticationKeyGenerator(new RandomAuthenticationKeyGenerator());
    return redisTokenStore;
  }


  /**
   * 配用来配置授权(authorization)以及令牌(token)的访问端点的令牌服务(token services)
   * (1) 存放token的位置
   * (2)
   *
   * @param endpoints 配置授权服务器端点的属性和增强的功能。
   */
  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
    endpoints.tokenStore(tokenStore())
        //资源所有者密码（password）授权类型的时候，请设置这个属性注入一个 AuthenticationManager 对象。
        .authenticationManager(authenticationManager)
        //设置用户名密码角色权限信息
        .userDetailsService(userDetailsService);
  }

  @Override
  public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
    /*允许表单的形式登录*/
    oauthServer.allowFormAuthenticationForClients();
  }


}
