package com.ywy.learn.query.listener;

import com.ywy.learn.command.article.api.event.*;
import com.ywy.learn.query.entry.ArticleEntry;
import com.ywy.learn.query.entry.CommentEntry;
import com.ywy.learn.query.repository.ArticleEntryRepository;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.MetaData;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * @author ve
 * @date 2019/3/29 20:38
 */
@Component
public class ArticleListener implements ArticleEventListener {

    @Autowired
    ArticleEntryRepository repository;

    @Override
    @EventHandler
    public void on(ArticleCreatedEvent event, MetaData metaData) {
        ArticleEntry articleEntry = new ArticleEntry();
        BeanUtils.copyProperties(event, articleEntry);
        repository.save(articleEntry);
    }

    @Override
    @EventHandler
    public void on(ArticleUpdatedEvent event, MetaData metaData) {
        ArticleEntry entry = repository.findOne(event.getId());
        BeanUtils.copyProperties(event, entry);
        repository.save(entry);
    }

    @Override
    @EventHandler
    public void on(ArticleCommentedEvent event, MetaData metaData) {
        ArticleEntry entry = repository.findOne(event.getId());
        BeanUtils.copyProperties(event, entry);
        entry.setComments(event.getComments().stream().map(commentId -> new CommentEntry(commentId)).collect(Collectors.toList()));
        repository.save(entry);
    }

    @Override
    @EventHandler
    public void on(ArticleRemovedEvent event, MetaData metaData) {
        repository.delete(event.getId());
    }

}
