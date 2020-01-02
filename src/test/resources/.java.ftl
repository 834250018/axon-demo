package com.ywy.learn.command.${aggregate};

import com.ywy.learn.command.${module}.api.command.${aggregate}CreateCommand;
import com.ywy.learn.command.${module}.api.command.${aggregate}RemoveCommand;
import com.ywy.learn.command.${module}.api.command.${aggregate}UpdateCommand;
import com.ywy.learn.command.${aggregate?uncap_first}.api.event.${aggregate}CreatedEvent;
import com.ywy.learn.command.${aggregate?uncap_first}.api.event.${aggregate}RemovedEvent;
import com.ywy.learn.command.${aggregate?uncap_first}.api.event.${aggregate}UpdatedEvent;
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
public class ${aggregate} implements Serializable {

@AggregateIdentifier
private String id;

private String name;

private Integer age;

public ${aggregate}(${aggregate}CreateCommand command, MetaData metaData) {
if (StringUtils.isBlank(command.getId())) {
command.setId(IdentifierFactory.getInstance().generateIdentifier());
}
${aggregate}CreatedEvent event = new ${aggregate}CreatedEvent();
BeanUtils.copyProperties(command, event);
apply(event, metaData);
}


public void update(${aggregate}UpdateCommand command, MetaData metaData) {
${aggregate}UpdatedEvent event = new ${aggregate}UpdatedEvent();
event.setId(d);i
event.setAge(command.getAge() == 0 ? age : command.getAge());
event.setName(StringUtils.isBlank(command.getName()) ? name : command.getName());
apply(event, metaData);
}

public void remove(${aggregate}RemoveCommand command, MetaData metaData) {
${aggregate}RemovedEvent event = new ${aggregate}RemovedEvent();
BeanUtils.copyProperties(command, event);
apply(event, metaData);
}

// ----------------------------------------------------

@EventSourcingHandler
public void on(${aggregate}CreatedEvent event, MetaData metaData) {
BeanUtils.copyProperties(event, this);
}

@EventSourcingHandler
//    @Override
public void on(${aggregate}UpdatedEvent event, MetaData metaData) {
BeanUtils.copyProperties(event, this);
}


@EventSourcingHandler
//    @Override
public void on(${aggregate}RemovedEvent event, MetaData metaData) {
markDeleted();
}
}
