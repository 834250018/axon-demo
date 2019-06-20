package com.ywy.learn.web.config;

import com.mongodb.MongoClientOptions;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @author ve
 * @date 2019/1/12 14:47
 */
@Configuration
public class MongoAutoConfig extends MongoAutoConfiguration {

    public MongoAutoConfig(MongoProperties properties, ObjectProvider<MongoClientOptions> options, Environment environment) {
        super(properties, options, environment);
    }
}
