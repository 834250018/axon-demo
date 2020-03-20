package com.ywy.learn.command.user;

import com.ywy.learn.command.user.api.command.*;
import com.ywy.learn.command.user.api.event.*;
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

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;
import static org.axonframework.commandhandling.model.AggregateLifecycle.markDeleted;

/**
 * @author ve
 * @date 2019/3/29 15:32
 */
@Aggregate
@NoArgsConstructor
@Data
public class User extends BaseAggregate implements UserCommandListener, UserEventListener {

    @AggregateIdentifier
    private String id;
    private String email;

    private String certId;

    private String lastToken;

    public User(UserCreateCommand command, MetaData metaData) {
        if (StringUtils.isBlank(command.getId())) {
            command.setId(IdentifierFactory.getInstance().generateIdentifier());
        }
        UserCreatedEvent event = new UserCreatedEvent();
        event.setId(command.getId());
        event.setEmail(command.getEmail());
        apply(event, metaData);
    }

    @Override
    public void handle(UserLoginCommand command, MetaData metaData) {
        UserLoginedEvent event = new UserLoginedEvent();
        event.setId(id);
        event.setLastToken(command.getLastToken());
        apply(event, metaData);
    }


    @Override
    public void handle(UserApplyCertCommand command, MetaData metaData) {
        UserCertApplyedEvent event = new UserCertApplyedEvent();
        event.setId(id);
        event.setCertId(command.getCertId());
        apply(event, metaData);
    }


    @Override
    public void handle(AuthRemoveCommand command, MetaData metaData) {
        AuthRemovedEvent event = new AuthRemovedEvent();
        event.setId(command.getUserId());
        apply(event, metaData);
    }

    @Override
    public void handle(UserUpdateCommand command, MetaData metaData) {
        UserUpdatedEvent event = new UserUpdatedEvent();
        event.setId(id);
        apply(event, metaData);
    }

    @Override
    public void remove(UserRemoveCommand command, MetaData metaData) {
        UserRemovedEvent event = new UserRemovedEvent();
        BeanUtils.copyProperties(command, event);
        apply(event, metaData);
    }

    // ----------------------------------------------------

    @Override
    @EventSourcingHandler
    public void on(UserCreatedEvent event, MetaData metaData) {
        applyMetaData(metaData);
        BeanUtils.copyProperties(event, this);
    }

    @Override
    @EventSourcingHandler
    public void on(UserUpdatedEvent event, MetaData metaData) {
        BeanUtils.copyProperties(event, this);
    }

    @Override
    public void on(AuthRemovedEvent event, MetaData metaData) {
        BeanUtils.copyProperties(event, this);
    }

    @Override
    @EventSourcingHandler
    public void on(UserLoginedEvent event, MetaData metaData) {
        BeanUtils.copyProperties(event, this);
    }

    @Override
    @EventSourcingHandler
    public void on(UserCertApplyedEvent event, MetaData metaData) {
        BeanUtils.copyProperties(event, this);
    }


    @Override
    @EventSourcingHandler
    public void on(UserRemovedEvent event, MetaData metaData) {
        markDeleted();
    }
}
