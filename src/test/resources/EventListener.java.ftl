package com.ywy.learn.command.${aggregate?uncap_first}.api.event;

import org.axonframework.messaging.MetaData;

/**
 * @author ve
 * @date 2020/3/20 15:31
 */
public interface I${aggregate?cap_first}EventListener {

    void on(${aggregate?cap_first}CreatedEvent event, MetaData metaData);

    void on(${aggregate?cap_first}UpdatedEvent event, MetaData metaData);

    void on(${aggregate?cap_first}LoginedEvent event, MetaData metaData);

    void on(${aggregate?cap_first}RemovedEvent event, MetaData metaData);
}
