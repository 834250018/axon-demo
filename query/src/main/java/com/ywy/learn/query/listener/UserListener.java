package com.ywy.learn.query.listener;

import com.ywy.learn.command.user.api.event.UserCreatedEvent;
import com.ywy.learn.command.user.api.event.UserRemovedEvent;
import com.ywy.learn.command.user.api.event.UserUpdatedEvent;
import com.ywy.learn.query.entry.UserEntry;
import com.ywy.learn.query.repository.UserEntryRepository;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.MetaData;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author ve
 * @date 2019/3/29 20:38
 */
@Component
public class UserListener {

    @Autowired
    UserEntryRepository repository;

    @EventHandler
    public void on(UserCreatedEvent event, MetaData metaData) {
        UserEntry userEntry = new UserEntry();
        BeanUtils.copyProperties(event, userEntry);
        repository.save(userEntry);
    }

    @EventHandler
//    @Override
    public void on(UserUpdatedEvent event, MetaData metaData) {
        UserEntry entry = repository.findOne(event.getUserId());
        BeanUtils.copyProperties(event, entry);
        repository.save(entry);
    }

    @EventHandler
//    @Override
    public void on(UserRemovedEvent event, MetaData metaData) {
        repository.delete(event.getUserId());
    }

}
