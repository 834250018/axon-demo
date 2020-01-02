package com.ywy.learn.web.config;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.convert.CustomConversions;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ve
 * @date 2019/1/12 14:47
 * <p>
 * 增加BigDecimal与Decimal128的转换器
 */
@Configuration
public class MongoConverterConfig extends MongoDataAutoConfiguration {

    public MongoConverterConfig(ApplicationContext applicationContext, MongoProperties properties) {
        super(applicationContext, properties);
    }

    @Override
    public MappingMongoConverter mappingMongoConverter(MongoDbFactory factory, MongoMappingContext context, BeanFactory beanFactory, CustomConversions conversions) {
        List<Object> list = new ArrayList<>();
        list.add(new BigDecimalToDecimal128Converter());
        list.add(new Decimal128ToBigDecimalConverter());
        return super.mappingMongoConverter(factory, context, beanFactory, new CustomConversions(list));
    }

}
