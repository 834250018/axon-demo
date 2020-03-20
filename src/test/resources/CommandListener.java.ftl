package com.ywy.learn.command.${aggregate?uncap_first}.api.command;

import org.axonframework.messaging.MetaData;

/**
 * @author ve
 * @date 2020/3/20 15:31
 */
public interface I${aggregate?cap_first}CommandListener {

    void handle(${aggregate?cap_first}UpdateCommand command, MetaData metaData);

    void handle(${aggregate?cap_first}LoginCommand command, MetaData metaData);

    void remove(${aggregate?cap_first}RemoveCommand command, MetaData metaData);
}
