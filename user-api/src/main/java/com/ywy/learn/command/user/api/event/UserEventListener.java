package com.ywy.learn.command.user.api.event;

import org.axonframework.messaging.MetaData;

/**
 * @author ve
 * @date 2020/3/20 15:31
 */
public interface UserEventListener {

    void on(UserCreatedEvent event, MetaData metaData);

    void on(UserUpdatedEvent event, MetaData metaData);

//    void on(AuthRemovedEvent event, MetaData metaData);

    void on(UserCertApplyedEvent event, MetaData metaData);

    void on(UserLoginedEvent event, MetaData metaData);

    void on(UserRemovedEvent event, MetaData metaData);
}
