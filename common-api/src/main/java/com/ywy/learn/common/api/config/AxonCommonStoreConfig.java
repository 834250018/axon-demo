package com.ywy.learn.common.api.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.axonframework.eventhandling.saga.repository.SagaStore;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.mongo.DefaultMongoTemplate;
import org.axonframework.mongo.MongoTemplate;
import org.axonframework.mongo.eventhandling.saga.repository.MongoSagaStore;
import org.axonframework.mongo.eventsourcing.eventstore.MongoEventStorageEngine;
import org.axonframework.mongo.eventsourcing.eventstore.documentpercommit.DocumentPerCommitStorageStrategy;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.json.JacksonSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author ve
 * @date 2019/3/29 11:57
 */
@Configuration
public class AxonCommonStoreConfig {

    private static final String HOST_PLACEHOLDER = "${spring.data.mongodb.command.host}";
    private static final String PORT_PLACEHOLDER = "${spring.data.mongodb.command.port}";
    private static final String USERNAME_PLACEHOLDER = "${spring.data.mongodb.command.username}";
    private static final String PWS_PLACEHOLDER = "${spring.data.mongodb.command.password}";
    private static final String DATABASE_PLACEHOLDER = "${spring.data.mongodb.command.database}";

    public MongoClient mongoClient(String host, int port, String username, String psw, String database) {
        MongoCredential credential = MongoCredential.createCredential(username, database, psw.toCharArray());
        ServerAddress serverAddress = new ServerAddress(host, port);
        MongoClient mongoClient = new MongoClient(serverAddress, Stream.of(credential).collect(Collectors.toList()));
        return mongoClient;
    }

    public MongoTemplate axonMongoTemplate(String host, int port, String username, String psw, String database) {
        DefaultMongoTemplate template = new DefaultMongoTemplate(mongoClient(host, port, username, psw, database), database);
        return template;
    }

    @Bean
    @Primary
    public Serializer axonJsonSerializer() {
        return new JacksonSerializer();
    }

    @Bean
    public EventStorageEngine eventStorageEngine(Serializer axonJsonSerializer,
                                                 @Value(HOST_PLACEHOLDER) String host,
                                                 @Value(PORT_PLACEHOLDER) String port,
                                                 @Value(USERNAME_PLACEHOLDER) String username,
                                                 @Value(PWS_PLACEHOLDER) String password,
                                                 @Value(DATABASE_PLACEHOLDER) String database) {
        return new MongoEventStorageEngine(axonJsonSerializer, null, axonMongoTemplate(host, Integer.valueOf(port), username, password, database), new DocumentPerCommitStorageStrategy());
    }

    @Bean
    public SagaStore sagaStore(Serializer axonJsonSerializer,
                               @Value(HOST_PLACEHOLDER) String host,
                               @Value(PORT_PLACEHOLDER) int port,
                               @Value(USERNAME_PLACEHOLDER) String username,
                               @Value(PWS_PLACEHOLDER) String password,
                               @Value(DATABASE_PLACEHOLDER) String database) {
        return new MongoSagaStore(axonMongoTemplate(host, port, username, password, database),
                axonJsonSerializer);
    }
}
