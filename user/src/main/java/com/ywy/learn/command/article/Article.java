package com.ywy.learn.command.article;

import com.ywy.learn.command.article.api.command.*;
import com.ywy.learn.command.article.api.event.*;
import com.ywy.learn.common.api.base.BaseAggregate;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.common.IdentifierFactory;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.messaging.MetaData;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;
import static org.axonframework.commandhandling.model.AggregateLifecycle.markDeleted;

/**
 * @author ve
 * @date 2019/3/29 15:32
 */
@Aggregate
@NoArgsConstructor
@Data
public class Article extends BaseAggregate implements ArticleCommandListener, ArticleEventListener {

    @AggregateIdentifier
    private String id;

    private String title;

    private String content;

    private List<String> comments;

    public Article(ArticleCreateCommand command, MetaData metaData) {
        if (StringUtils.isBlank(command.getId())) {
            command.setId(IdentifierFactory.getInstance().generateIdentifier());
        }
        ArticleCreatedEvent event = new ArticleCreatedEvent();
        BeanUtils.copyProperties(command, event);
        event.setComments(new ArrayList<>());
        apply(event, metaData);
    }

    @Override
    public void handle(ArticleUpdateCommand command, MetaData metaData) {
        ArticleUpdatedEvent event = new ArticleUpdatedEvent();
        BeanUtils.copyProperties(this, event);
        BeanUtils.copyProperties(command, event);
        apply(event, metaData);
    }

    @Override
    public void handle(ArticleCommentCommand command, MetaData metaData) {
        ArticleCommentedEvent event = new ArticleCommentedEvent();
        BeanUtils.copyProperties(this, event);
        event.setId(id);
        event.setComments(comments);
        event.getComments().add(command.getCommentId());
        apply(event, metaData);
    }

    @Override
    public void remove(ArticleRemoveCommand command, MetaData metaData) {
        ArticleRemovedEvent event = new ArticleRemovedEvent();
        BeanUtils.copyProperties(command, event);
        apply(event, metaData);
    }

// ----------------------------------------------------

    @Override
    @EventSourcingHandler
    public void on(ArticleCreatedEvent event, MetaData metaData) {
        BeanUtils.copyProperties(event, this);
    }

    @Override
    @EventSourcingHandler
    public void on(ArticleUpdatedEvent event, MetaData metaData) {
        BeanUtils.copyProperties(event, this);
    }

    @Override
    @EventSourcingHandler
    public void on(ArticleCommentedEvent event, MetaData metaData) {
        BeanUtils.copyProperties(event, this);
    }


    @Override
    @EventSourcingHandler
    public void on(ArticleRemovedEvent event, MetaData metaData) {
        markDeleted();
    }
}
