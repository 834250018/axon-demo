package com.ywy.learn.command.article;

import com.ywy.learn.command.article.api.command.ArticleCommentCommand;
import com.ywy.learn.command.article.api.command.ArticleCreateCommand;
import com.ywy.learn.command.article.api.command.ArticleRemoveCommand;
import com.ywy.learn.command.article.api.command.ArticleUpdateCommand;
import com.ywy.learn.command.article.api.event.ArticleCommentedEvent;
import com.ywy.learn.command.article.api.event.ArticleCreatedEvent;
import com.ywy.learn.command.article.api.event.ArticleRemovedEvent;
import com.ywy.learn.command.article.api.event.ArticleUpdatedEvent;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.common.IdentifierFactory;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.messaging.MetaData;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
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
public class Article implements Serializable {

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


    public void update(ArticleUpdateCommand command, MetaData metaData) {
        ArticleUpdatedEvent event = new ArticleUpdatedEvent();
        BeanUtils.copyProperties(this, event);
        BeanUtils.copyProperties(command, event);
        apply(event, metaData);
    }

    public void comment(ArticleCommentCommand command, MetaData metaData) {
        ArticleCommentedEvent event = new ArticleCommentedEvent();
        BeanUtils.copyProperties(this, event);
        event.setId(id);
        event.setComments(comments);
        event.getComments().add(command.getCommentId());
        apply(event, metaData);
    }

    public void remove(ArticleRemoveCommand command, MetaData metaData) {
        ArticleRemovedEvent event = new ArticleRemovedEvent();
        BeanUtils.copyProperties(command, event);
        apply(event, metaData);
    }

// ----------------------------------------------------

    @EventSourcingHandler
    public void on(ArticleCreatedEvent event, MetaData metaData) {
        BeanUtils.copyProperties(event, this);
    }

    @EventSourcingHandler
//    @Override
    public void on(ArticleUpdatedEvent event, MetaData metaData) {
        BeanUtils.copyProperties(event, this);
    }

    @EventSourcingHandler
//    @Override
    public void on(ArticleCommentedEvent event, MetaData metaData) {
        BeanUtils.copyProperties(event, this);
    }


    @EventSourcingHandler
//    @Override
    public void on(ArticleRemovedEvent event, MetaData metaData) {
        markDeleted();
    }
}
