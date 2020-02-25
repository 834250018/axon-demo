package com.ywy.learn.command.admin;

import com.ywy.learn.command.admin.api.command.AdminCreateCommand;
import com.ywy.learn.command.admin.api.command.AdminLoginCommand;
import com.ywy.learn.command.admin.api.command.AdminRemoveCommand;
import com.ywy.learn.command.admin.api.command.AdminUpdateCommand;
import com.ywy.learn.command.admin.api.event.AdminCreatedEvent;
import com.ywy.learn.command.admin.api.event.AdminLoginedEvent;
import com.ywy.learn.command.admin.api.event.AdminRemovedEvent;
import com.ywy.learn.command.admin.api.event.AdminUpdatedEvent;
import com.ywy.learn.infrastructure.base.BaseAggregate;
import com.ywy.learn.infrastructure.exception.BusinessException;
import com.ywy.learn.infrastructure.util.DigestKit;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.common.IdentifierFactory;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.messaging.MetaData;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.util.Base64;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;
import static org.axonframework.commandhandling.model.AggregateLifecycle.markDeleted;

/**
 * @author ve
 * @date 2019/3/29 15:32
 */
@Aggregate
@NoArgsConstructor
@Data
public class Admin extends BaseAggregate {

    @AggregateIdentifier
    private String id;

    private String username;

    // sha256(sha256(用户密码), salt)
    private String password;

    private String lastToken;

    private String salt;

    public Admin(AdminCreateCommand command, MetaData metaData) {
        if (StringUtils.isBlank(command.getId())) {
            command.setId(IdentifierFactory.getInstance().generateIdentifier());
        }
        AdminCreatedEvent event = new AdminCreatedEvent();
        BeanUtils.copyProperties(command, event);
        event.setSalt(Base64.getEncoder().encodeToString(RandomUtils.nextBytes(16)));
        event.setPassword(DigestKit.sha256AndSalt(event.getPassword(), event.getSalt()));
        apply(event, metaData);
    }


    public void update(AdminUpdateCommand command, MetaData metaData) {
        AdminUpdatedEvent event = new AdminUpdatedEvent();
        BeanUtils.copyProperties(this, event);
        BeanUtils.copyProperties(command, event);
        apply(event, metaData);
    }


    public void update(AdminLoginCommand command, MetaData metaData) {
        String pwd = DigestKit.sha256AndSalt(command.getPassword(), salt);
        if (!password.equals(pwd)) {
            throw new BusinessException("用户不存在");
        }
        AdminLoginedEvent event = new AdminLoginedEvent();
        event.setId(id);
        event.setLastToken(command.getLastToken());
        apply(event, metaData);
    }

    public void remove(AdminRemoveCommand command, MetaData metaData) {
        AdminRemovedEvent event = new AdminRemovedEvent();
        BeanUtils.copyProperties(command, event);
        apply(event, metaData);
    }

// ----------------------------------------------------

    @EventSourcingHandler
    public void on(AdminCreatedEvent event, MetaData metaData) {
        applyMetaData(metaData);
        BeanUtils.copyProperties(event, this);
    }

    @EventSourcingHandler
//    @Override
    public void on(AdminUpdatedEvent event, MetaData metaData) {
        BeanUtils.copyProperties(event, this);
    }

    @EventSourcingHandler
//    @Override
    public void on(AdminLoginedEvent event, MetaData metaData) {
        BeanUtils.copyProperties(event, this);
    }


    @EventSourcingHandler
//    @Override
    public void on(AdminRemovedEvent event, MetaData metaData) {
        markDeleted();
    }
}
