package com.killstan.bolg.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: Kill_Stan
 * @Date: 2022/3/24 10:22
 * @Description:
 */
@Configuration
@MapperScan("com.killstan.bolg.mapper")
public class MybatisConfig {

    /**
     * @title mybatis-plus 分页插件
     * @description
     * @author admin
     * @updateTime
     * @throws
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
}
