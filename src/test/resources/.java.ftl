package com.ywy.learn.command.${aggregate};

import com.ywy.learn.command.${aggregate?uncap_first}.api.command.${aggregate?cap_first}CreateCommand;
import com.ywy.learn.command.${aggregate?uncap_first}.api.command.${aggregate?cap_first}RemoveCommand;
import com.ywy.learn.command.${aggregate?uncap_first}.api.command.${aggregate?cap_first}UpdateCommand;
import com.ywy.learn.command.${aggregate?uncap_first}.api.event.${aggregate?cap_first}CreatedEvent;
import com.ywy.learn.command.${aggregate?uncap_first}.api.event.${aggregate?cap_first}RemovedEvent;
import com.ywy.learn.command.${aggregate?uncap_first}.api.event.${aggregate?cap_first}UpdatedEvent;
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
public class ${aggregate?cap_first} extends BaseAggregate {

@AggregateIdentifier
private String id;

private String name;

private Integer age;

public ${aggregate?cap_first}(${aggregate?cap_first}CreateCommand command, MetaData metaData) {
if (StringUtils.isBlank(command.getId())) {
command.setId(IdentifierFactory.getInstance().generateIdentifier());
}
${aggregate?cap_first}CreatedEvent event = new ${aggregate?cap_first}CreatedEvent();
BeanUtils.copyProperties(command, event);
apply(event, metaData);
}


public void update(${aggregate?cap_first}UpdateCommand command, MetaData metaData) {
${aggregate?cap_first}UpdatedEvent event = new ${aggregate?cap_first}UpdatedEvent();
BeanUtils.copyProperties(this, event);
BeanUtils.copyProperties(command, event);
apply(event, metaData);
}

public void remove(${aggregate?cap_first}RemoveCommand command, MetaData metaData) {
${aggregate?cap_first}RemovedEvent event = new ${aggregate?cap_first}RemovedEvent();
BeanUtils.copyProperties(command, event);
apply(event, metaData);
}

// ----------------------------------------------------

@EventSourcingHandler
public void on(${aggregate?cap_first}CreatedEvent event, MetaData metaData) {
        applyMetaData(metaData);
BeanUtils.copyProperties(event, this);
}

@EventSourcingHandler
//    @Override
public void on(${aggregate?cap_first}UpdatedEvent event, MetaData metaData) {
BeanUtils.copyProperties(event, this);
}


@EventSourcingHandler
//    @Override
public void on(${aggregate?cap_first}RemovedEvent event, MetaData metaData) {
markDeleted();
}
}
