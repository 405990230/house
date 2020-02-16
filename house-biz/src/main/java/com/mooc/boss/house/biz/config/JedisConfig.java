package com.mooc.boss.house.biz.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.util.Hashing;
import redis.clients.util.Sharded;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableCaching
@Slf4j
public class JedisConfig implements InitializingBean {
    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.connTimeout}")
    private int timeout;

    @Value("${spring.redis.maxActive}")
    private int maxActive;

    @Value("${spring.redis.maxIdle}")
    private int maxIdle;
    @Value("${spring.redis.minIdle}")
    private int minIdle;

    @Value("${spring.redis.maxWaitMillis}")
    private Long maxWaitMillis;

    /**
     * 初始化jedis连接池
     */
    @Bean
    public JedisPool jedisPoolConfig() {
        // 创建jedis池配置实例
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();

        jedisPoolConfig.setMaxTotal(maxActive);
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMinIdle(minIdle);

        // 连接池最大阻塞等待时间
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);

        // #jedis调用borrowObject方法时，是否进行有效检查
        jedisPoolConfig.setTestOnBorrow(Boolean.valueOf(true));

        // #jedis调用returnObject方法时，是否进行有效检查
        jedisPoolConfig.setTestOnReturn(Boolean.valueOf(true));

        JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout, password);
        return jedisPool;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("****************正在启动Redis****************");
        log.info("******Redis-Url:" + host + ":" + port + "******");
        log.info("********************************************");
    }
}
