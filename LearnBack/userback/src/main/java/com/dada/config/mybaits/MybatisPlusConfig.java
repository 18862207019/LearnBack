package com.dada.config.mybaits;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.MybatisMapWrapperFactory;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.annotation.PostConstruct;

/**
 * MybatisPlus配置类
 */
@Configuration
public class MybatisPlusConfig {

    @Autowired
    private MybatisPlusProperties mybatisPlusProperties;

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    /**
     *  实例化bean(MybatisPlusConfig)之前的配置操作
     */
    @PostConstruct
    public void mybatisConfiguration() {
        MybatisConfiguration configuration = mybatisPlusProperties.getConfiguration();
        //注册下划线转驼峰
        configuration.setObjectWrapperFactory(new MybatisMapWrapperFactory());
    }
}
