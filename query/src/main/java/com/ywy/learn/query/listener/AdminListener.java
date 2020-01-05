package com.ywy.learn.query.listener;

import com.ywy.learn.command.admin.api.event.AdminCreatedEvent;
import com.ywy.learn.command.admin.api.event.AdminRemovedEvent;
import com.ywy.learn.command.admin.api.event.AdminUpdatedEvent;
import com.ywy.learn.query.entry.AdminEntry;
import com.ywy.learn.query.repository.AdminEntryRepository;
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
public class AdminListener {

    @Autowired
    AdminEntryRepository repository;

    @EventHandler
    public void on(AdminCreatedEvent event, MetaData metaData) {
        AdminEntry adminEntry = new AdminEntry();
        BeanUtils.copyProperties(event, adminEntry);
        repository.save(adminEntry);
    }

    @EventHandler
//    @Override
    public void on(AdminUpdatedEvent event, MetaData metaData) {
        AdminEntry entry = repository.findOne(event.getId());
        BeanUtils.copyProperties(event, entry);
        repository.save(entry);
    }

    @EventHandler
//    @Override
    public void on(AdminRemovedEvent event, MetaData metaData) {
        repository.delete(event.getId());
    }

}
