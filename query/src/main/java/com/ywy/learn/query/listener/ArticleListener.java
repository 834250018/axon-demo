package com.ywy.learn.query.listener;

import com.ywy.learn.command.article.api.event.ArticleCreatedEvent;
import com.ywy.learn.command.article.api.event.ArticleRemovedEvent;
import com.ywy.learn.command.article.api.event.ArticleUpdatedEvent;
import com.ywy.learn.query.entry.ArticleEntry;
import com.ywy.learn.query.repository.ArticleEntryRepository;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.MetaData;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author ve
 * @date 2019/3/29 20:38
 */
@Component
public class ArticleListener {

    @Autowired
    ArticleEntryRepository repository;

    @EventHandler
    public void on(ArticleCreatedEvent event, MetaData metaData) {
        ArticleEntry articleEntry = new ArticleEntry();
        BeanUtils.copyProperties(event, articleEntry);
        repository.save(articleEntry);
    }

    @EventHandler
//    @Override
    public void on(ArticleUpdatedEvent event, MetaData metaData) {
        ArticleEntry entry = repository.findOne(event.getId());
        BeanUtils.copyProperties(event, entry);
        repository.save(entry);
    }

    @EventHandler
//    @Override
    public void on(ArticleRemovedEvent event, MetaData metaData) {
        repository.delete(event.getId());
    }

}
