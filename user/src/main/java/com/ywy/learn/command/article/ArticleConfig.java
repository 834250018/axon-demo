package com.ywy.learn.command.article;

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
public class ArticleConfig {

//    @Autowired
//    private ;

//    @Autowired
//    private ;

    @Bean
    @Scope("prototype")
    public Article article() {
        return new Article();
    }

    @Bean
    public AggregateFactory<Article> articleAggregateFactory() {
        SpringPrototypeAggregateFactory<Article> springPrototypeAggregateFactory = new SpringPrototypeAggregateFactory<>();
        springPrototypeAggregateFactory.setPrototypeBeanName("article");
        return springPrototypeAggregateFactory;
    }

    @Bean
    public Repository<Article> articleRepository(AggregateFactory<Article> factory, JCacheAdapter cacheAdapter, EventStore eventStore, Snapshotter snapshotter) {
        EventCountSnapshotTriggerDefinition snapshotTriggerDefinition = new EventCountSnapshotTriggerDefinition(snapshotter, 1);

        return new CachingEventSourcingRepository<>(factory, eventStore, cacheAdapter, snapshotTriggerDefinition);

//        repository;
    }

}
