package com.ywy.learn.command.article.api.command;

import org.axonframework.messaging.MetaData;

/**
 * @author ve
 * @date 2020/3/20 15:31
 */
public interface ArticleCommandListener {

    void handle(ArticleCommentCommand command, MetaData metaData);

    void handle(ArticleUpdateCommand command, MetaData metaData);

    void remove(ArticleRemoveCommand command, MetaData metaData);
}
