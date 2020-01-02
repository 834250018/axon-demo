package com.ywy.learn.command.article;

import com.ywy.learn.command.article.api.command.ArticleCreateCommand;
import com.ywy.learn.command.article.api.command.ArticleRemoveCommand;
import com.ywy.learn.command.article.api.command.ArticleUpdateCommand;
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

private String name;

private Integer age;

public Article(ArticleCreateCommand command, MetaData metaData) {
if (StringUtils.isBlank(command.getId())) {
command.setId(IdentifierFactory.getInstance().generateIdentifier());
}
ArticleCreatedEvent event = new ArticleCreatedEvent();
BeanUtils.copyProperties(command, event);
apply(event, metaData);
}


public void update(ArticleUpdateCommand command, MetaData metaData) {
ArticleUpdatedEvent event = new ArticleUpdatedEvent();
event.setId(id);
event.setAge(command.getAge() == 0 ? age : command.getAge());
event.setName(StringUtils.isBlank(command.getName()) ? name : command.getName());
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
public void on(ArticleRemovedEvent event, MetaData metaData) {
markDeleted();
}
}
