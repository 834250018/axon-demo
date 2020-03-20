package com.ywy.learn.command.admin;

import com.ywy.learn.command.admin.api.command.*;
import com.ywy.learn.common.api.base.BaseHandle;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.Aggregate;
import org.axonframework.commandhandling.model.Repository;
import org.axonframework.messaging.MetaData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author ve
 * @date 2019/3/29 15:30
 */
@Component
public class AdminHandle extends BaseHandle implements AdminCommandListener {

    @Autowired
    Repository<Admin> repository;

    @CommandHandler
    public void handle(AdminCreateCommand command, MetaData metaData) throws Exception {
        repository.newInstance(() -> new Admin(command, metaData));
    }

    @CommandHandler
    @Override
    public void handle(AdminUpdateCommand command, MetaData metaData) {
        Aggregate<Admin> target = repository.load(command.getId());
        checkAuthorization(target, metaData);
        target.execute(aggregate -> aggregate.handle(command, metaData));
    }

    @CommandHandler
    @Override
    public void handle(AdminLoginCommand command, MetaData metaData) {
        Aggregate<Admin> target = repository.load(command.getId());
        checkAuthorization(target, metaData);
        target.execute(aggregate -> aggregate.handle(command, metaData));
    }

    @CommandHandler
    public void remove(AdminRemoveCommand command, MetaData metaData) {
        Aggregate<Admin> target = repository.load(command.getId());
        checkAuthorization(target, metaData);
        target.execute(aggregate -> aggregate.remove(command, metaData));
    }
}
