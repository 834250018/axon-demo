package com.ywy.learn.command.user;

import com.ywy.learn.command.user.api.command.UserCreateCommand;
import com.ywy.learn.command.user.api.command.UserRemoveCommand;
import com.ywy.learn.command.user.api.command.UserUpdateCommand;
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
public class UserHandle {

    @Autowired
    Repository<User> repository;

    @CommandHandler
    public void handle(UserCreateCommand command, MetaData metaData) throws Exception {
        repository.newInstance(() -> new User(command, metaData));
    }

    @CommandHandler
    public void handle(UserUpdateCommand command, MetaData metaData) {
        Aggregate<User> target = repository.load(command.getUserId());
//        checkAuthorization(target,metaData);
        target.execute(aggregate -> aggregate.update(command, metaData));
    }

    @CommandHandler
    public void handle(UserRemoveCommand command, MetaData metaData) {
        Aggregate<User> target = repository.load(command.getUserId());
//        checkAuthorization(target,metaData);
        target.execute(aggregate -> aggregate.remove(command, metaData));
    }
}
