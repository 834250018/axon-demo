package com.ywy.learn.command.${aggregate};

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
public class ${aggregate?cap_first}Config {

// @Autowired
// private ;

// @Autowired
// private ;

@Bean
@Scope("prototype")
public ${aggregate?cap_first} ${aggregate?uncap_first}() {
return new ${aggregate?cap_first}();
}

@Bean
public AggregateFactory<${aggregate?cap_first}> ${aggregate?uncap_first}AggregateFactory() {
    SpringPrototypeAggregateFactory<${aggregate?cap_first}> springPrototypeAggregateFactory = new SpringPrototypeAggregateFactory<>();
        springPrototypeAggregateFactory.setPrototypeBeanName("${aggregate?uncap_first}");
        return springPrototypeAggregateFactory;
        }

        @Bean
        public Repository<${aggregate?cap_first}> ${aggregate?uncap_first}Repository(AggregateFactory<${aggregate?cap_first}> factory, JCacheAdapter cacheAdapter, EventStore eventStore, Snapshotter snapshotter) {
                EventCountSnapshotTriggerDefinition snapshotTriggerDefinition = new EventCountSnapshotTriggerDefinition(snapshotter, 1);

                // CachingEventSourcingRepository<${aggregate?cap_first}> repository = new CachingEventSourcingRepository<>(factory, eventStore, cacheAdapter, snapshotTriggerDefinition);
                return new CachingEventSourcingRepository<>(factory, eventStore, cacheAdapter, snapshotTriggerDefinition);

                    // repository;
                    }

                    }
