package com.ywy.command.user;

import org.axonframework.commandhandling.model.Repository;
import org.axonframework.common.caching.JCacheAdapter;
import org.axonframework.eventsourcing.AggregateFactory;
import org.axonframework.eventsourcing.CachingEventSourcingRepository;
import org.axonframework.eventsourcing.EventCountSnapshotTriggerDefinition;
import org.axonframework.eventsourcing.Snapshotter;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.spring.eventsourcing.SpringPrototypeAggregateFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * @author ve
 * @date 2019/4/3 14:32
 */
@Configuration
public class UserConfig {

    @Autowired
    private EventStore eventStore;

    @Autowired
    private Snapshotter snapshotter;

    @Bean
    @Scope("prototype")
    public User user() {
        return new User();
    }

    @Bean
    public AggregateFactory<User> userAggregateFactory() {
        SpringPrototypeAggregateFactory<User> springPrototypeAggregateFactory = new SpringPrototypeAggregateFactory<>();
        springPrototypeAggregateFactory.setPrototypeBeanName("user");
        return springPrototypeAggregateFactory;
    }

    @Bean
    public Repository<User> userRepository(AggregateFactory<User> factory, JCacheAdapter cacheAdapter) {
        EventCountSnapshotTriggerDefinition snapshotTriggerDefinition = new EventCountSnapshotTriggerDefinition(snapshotter, 1);

        CachingEventSourcingRepository<User> repository = new CachingEventSourcingRepository<>(factory, eventStore, cacheAdapter, snapshotTriggerDefinition);

        return repository;
    }

}
