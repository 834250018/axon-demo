package com.ywy.learn.command.article.api.event;

import org.axonframework.messaging.MetaData;

/**
 * @author ve
 * @date 2020/3/20 15:31
 */
public interface ArticleEventListener {

    void on(ArticleCreatedEvent event, MetaData metaData);

    void on(ArticleUpdatedEvent event, MetaData metaData);

    void on(ArticleCommentedEvent event, MetaData metaData);

    void on(ArticleRemovedEvent event, MetaData metaData);
}
