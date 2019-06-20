package com.ywy.command.user;

import com.ywy.command.user.api.command.UserCreateCommand;
import com.ywy.command.user.api.command.UserRemoveCommand;
import com.ywy.command.user.api.command.UserUpdateCommand;
import com.ywy.command.user.api.event.UserCreatedEvent;
import com.ywy.command.user.api.event.UserRemovedEvent;
import com.ywy.command.user.api.event.UserUpdatedEvent;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
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

    public User(UserCreateCommand command, MetaData metaData) {
        if (StringUtils.isBlank(command.getUserId())) {
            command.setUserId(IdentifierFactory.getInstance().generateIdentifier());
        }
        UserCreatedEvent event = new UserCreatedEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event, metaData);
    }


    public void update(UserUpdateCommand command, MetaData metaData) {
        UserUpdatedEvent event = new UserUpdatedEvent();
        BeanUtils.copyProperties(this, event);
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
