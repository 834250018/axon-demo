package com.ywy.learn.command.user.api.command;

import org.axonframework.messaging.MetaData;

/**
 * @author ve
 * @date 2020/3/20 15:31
 */
public interface UserCommandListener {

    void handle(AuthRemoveCommand command, MetaData metaData);

    void handle(UserUpdateCommand command, MetaData metaData);

    void handle(UserApplyCertCommand command, MetaData metaData);

    void handle(UserLoginCommand command, MetaData metaData);

    void remove(UserRemoveCommand command, MetaData metaData);
}
