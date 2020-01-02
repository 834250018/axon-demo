package com.ywy.learn.infrastructure.config;

import com.ywy.learn.infrastructure.util.CacheUtils;
import org.axonframework.common.caching.JCacheAdapter;
import org.axonframework.spring.eventsourcing.SpringAggregateSnapshotterFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Axon框架通用配置
 *
 * @author ve
 * @create 2018-01-26 下午5:44
 **/
@Configuration
public class AxonConfig {

    /**
     * Axon缓存适配器, 适配后缓存实例可供Axon框架使用
     *
     * @return
     */
    @Bean
    public JCacheAdapter cacheAdapter() {
        return new JCacheAdapter(CacheUtils.createCacheInstance("cache"));
    }

    /**
     * 快照生成器工厂
     *
     * @return
     */
    @Bean
    public SpringAggregateSnapshotterFactoryBean springAggregateSnapshotterFactoryBean() {
        return new SpringAggregateSnapshotterFactoryBean();
    }


}
