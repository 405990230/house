package com1.mooc.boss.house.web.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "spring.httpclient")
public class HttpClientProperties {

    private Integer connectTimeOut = 1000;

    private Integer socketTimeOut = 10000;

    private String agent = "agent";
    private Integer maxConnPerRoute = 10;
    private Integer maxConnTotaol = 50;

}
