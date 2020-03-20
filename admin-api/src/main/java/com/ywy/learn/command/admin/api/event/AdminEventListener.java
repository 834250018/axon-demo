package com.ywy.learn.command.admin.api.event;

import org.axonframework.messaging.MetaData;

/**
 * @author ve
 * @date 2020/3/20 15:31CommandListener.java
 */
public interface AdminEventListener {

    void on(AdminCreatedEvent event, MetaData metaData);

    void on(AdminUpdatedEvent event, MetaData metaData);

    void on(AdminLoginedEvent event, MetaData metaData);

    void on(AdminRemovedEvent event, MetaData metaData);
}
