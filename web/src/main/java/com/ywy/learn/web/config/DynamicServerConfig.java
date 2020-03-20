package com.ywy.learn.web.config;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * 占位符 配置,启动时占位符未找到跳过异常
 *
 * @author ve
 * @create 2018-08-09 上午12:03
 */

@Configuration
public class DynamicServerConfig extends PropertySourcesPlaceholderConfigurer {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        setIgnoreUnresolvablePlaceholders(true);
        super.postProcessBeanFactory(beanFactory);
    }
}
