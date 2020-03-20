package com.ywy.learn.command.admin;

import org.axonframework.commandhandling.model.Repository;
import org.axonframework.common.caching.JCacheAdapter;
import org.axonframework.eventsourcing.AggregateFactory;
import org.axonframework.eventsourcing.CachingEventSourcingRepository;
import org.axonframework.eventsourcing.EventCountSnapshotTriggerDefinition;
import org.axonframework.eventsourcing.Snapshotter;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.spring.eventsourcing.SpringPrototypeAggregateFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * @author ve
 * @date 2019/4/3 14:32
 */
@Configuration
public class AdminConfig {

//    @Autowired
//    private ;

//    @Autowired
//    private ;

    @Bean
    @Scope("prototype")
    public Admin admin() {
        return new Admin();
    }

    @Bean
    public AggregateFactory<Admin> adminAggregateFactory() {
        SpringPrototypeAggregateFactory<Admin> springPrototypeAggregateFactory = new SpringPrototypeAggregateFactory<>();
        springPrototypeAggregateFactory.setPrototypeBeanName("admin");
        return springPrototypeAggregateFactory;
    }

    @Bean
    public Repository<Admin> adminRepository(AggregateFactory<Admin> factory, JCacheAdapter cacheAdapter, EventStore eventStore, Snapshotter snapshotter) {
        EventCountSnapshotTriggerDefinition snapshotTriggerDefinition = new EventCountSnapshotTriggerDefinition(snapshotter, 1);

        return new CachingEventSourcingRepository<>(factory, eventStore, cacheAdapter, snapshotTriggerDefinition);

//         repository;
    }

}
