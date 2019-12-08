package com.dada.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 安全验证服务器
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
/**
 * prePostEnabled 开启权限注解
 * 使用表达式时间方法级别的安全性 4个注解可用
 * @PreAuthorize 在方法调用之前, 基于表达式的计算结果来限制对方法的访问
 * @PostAuthorize 允许方法调用, 但是如果表达式计算结果为false, 将抛出一个安全性异常
 * @PostFilter 允许方法调用, 但必须按照表达式来过滤方法的结果
 * @PreFilter 允许方法调用, 但必须在进入方法之前过滤输入值
 */
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**自己的userDetailService*/
    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    /**密码加密*/
    @Bean
    public BCryptPasswordEncoder passwordEncodery() {
        return new BCryptPasswordEncoder();
    }


    /**登陆之后的才能授权*/
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /*将我们的模型和oauth2的模型对接一次*/
        auth.userDetailsService(userDetailsService());
    }


    /**忽略接口/oauth/check_token*/
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/oauth/check_token");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .exceptionHandling()
                .and()
                .authorizeRequests().antMatchers("/**/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic().disable();
    }


}
