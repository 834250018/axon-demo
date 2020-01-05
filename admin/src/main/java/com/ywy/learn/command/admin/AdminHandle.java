package com.ywy.learn.command.admin;

import com.ywy.learn.command.admin.api.command.AdminCreateCommand;
import com.ywy.learn.command.admin.api.command.AdminRemoveCommand;
import com.ywy.learn.command.admin.api.command.AdminUpdateCommand;
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
public class AdminHandle {

    @Autowired
    Repository<Admin> repository;

    @CommandHandler
    public void handle(AdminCreateCommand command, MetaData metaData) throws Exception {
        repository.newInstance(() -> new Admin(command, metaData));
    }

    @CommandHandler
    public void handle(AdminUpdateCommand command, MetaData metaData) {
        Aggregate<Admin> target = repository.load(command.getId());
        //        checkAuthorization(target,metaData);
        target.execute(aggregate -> aggregate.update(command, metaData));
    }

    @CommandHandler
    public void handle(AdminRemoveCommand command, MetaData metaData) {
        Aggregate<Admin> target = repository.load(command.getId());
        //        checkAuthorization(target,metaData);
        target.execute(aggregate -> aggregate.remove(command, metaData));
    }
}
