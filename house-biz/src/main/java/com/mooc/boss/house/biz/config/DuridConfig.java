package com.mooc.boss.house.biz.config;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.google.common.collect.Lists;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.logging.Filter;

@Configuration
public class DuridConfig {
    @ConfigurationProperties(prefix = "spring.datasource.druid")
    @Bean(initMethod = "init", destroyMethod = "close")
    public DruidDataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setProxyFilters(Lists.newArrayList(statFilter()));
        return dataSource;
    }

    //druid提供了打印慢sql请求的日志的工具
    @Bean
    public StatFilter statFilter() {
        StatFilter filter = new StatFilter();
        filter.setSlowSqlMillis(5000);//多少时间为慢
        filter.setLogSlowSql(true);//是否打印
        filter.setMergeSql(true);//是否合并
        return filter;
    }

    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        return new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
    }
}
