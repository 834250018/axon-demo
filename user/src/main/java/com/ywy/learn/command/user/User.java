package com.ywy.learn.command.user;

import com.ywy.learn.command.user.api.command.UserCreateCommand;
import com.ywy.learn.command.user.api.command.UserRemoveCommand;
import com.ywy.learn.command.user.api.command.UserUpdateCommand;
import com.ywy.learn.command.user.api.event.UserCreatedEvent;
import com.ywy.learn.command.user.api.event.UserRemovedEvent;
import com.ywy.learn.command.user.api.event.UserUpdatedEvent;
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
public class User implements Serializable {

    @AggregateIdentifier
    private String userId;

    private String name;

    private Integer age;

    public User(UserCreateCommand command, MetaData metaData) {
        if (StringUtils.isBlank(command.getUserId())) {
            command.setUserId(IdentifierFactory.getInstance().generateIdentifier());
        }
        UserCreatedEvent event = new UserCreatedEvent();
        BeanUtils.copyProperties(command, event);
        apply(event, metaData);
    }


    public void update(UserUpdateCommand command, MetaData metaData) {
        UserUpdatedEvent event = new UserUpdatedEvent();
        event.setUserId(userId);
        event.setAge(command.getAge() == 0 ? age : command.getAge());
        event.setName(StringUtils.isBlank(command.getName()) ? name : command.getName());
        apply(event, metaData);
    }

    public void remove(UserRemoveCommand command, MetaData metaData) {
        UserRemovedEvent event = new UserRemovedEvent();
        BeanUtils.copyProperties(command, event);
        apply(event, metaData);
    }

    // ----------------------------------------------------

    @EventSourcingHandler
    public void on(UserCreatedEvent event, MetaData metaData) {
        BeanUtils.copyProperties(event, this);
    }

    @EventSourcingHandler
//    @Override
    public void on(UserUpdatedEvent event, MetaData metaData) {
        BeanUtils.copyProperties(event, this);
    }


    @EventSourcingHandler
//    @Override
    public void on(UserRemovedEvent event, MetaData metaData) {
        markDeleted();
    }
}
