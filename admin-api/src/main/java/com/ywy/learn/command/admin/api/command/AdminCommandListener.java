package com.ywy.learn.command.admin.api.command;

import org.axonframework.messaging.MetaData;

/**
 * @author ve
 * @date 2020/3/20 15:31
 */
public interface AdminCommandListener {

    void handle(AdminUpdateCommand command, MetaData metaData);

    void handle(AdminLoginCommand command, MetaData metaData);

    void remove(AdminRemoveCommand command, MetaData metaData);
}
