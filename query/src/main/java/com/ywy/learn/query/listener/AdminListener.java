package com.ywy.learn.query.listener;

import com.alibaba.fastjson.JSON;
import com.ywy.learn.command.admin.api.event.AdminCreatedEvent;
import com.ywy.learn.command.admin.api.event.AdminLoginedEvent;
import com.ywy.learn.command.admin.api.event.AdminRemovedEvent;
import com.ywy.learn.command.admin.api.event.AdminUpdatedEvent;
import com.ywy.learn.query.entry.AdminEntry;
import com.ywy.learn.query.repository.AdminEntryRepository;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.MetaData;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author ve
 * @date 2019/3/29 20:38
 */
@Component
public class AdminListener{

    @Autowired
    AdminEntryRepository repository;
    @Autowired
    StringRedisTemplate redisTemplate;

    @EventHandler
    public void on(AdminCreatedEvent event, MetaData metaData) {
        AdminEntry adminEntry = new AdminEntry();
        BeanUtils.copyProperties(event, adminEntry);
        adminEntry.applyMetaData(metaData);
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
    public void on(AdminLoginedEvent event, MetaData metaData) {
        AdminEntry entry = repository.findOne(event.getId());
        entry.setLastToken(event.getLastToken());
        repository.save(entry);
        redisTemplate.opsForValue().set(entry.getLastToken(), JSON.toJSONString(entry), 1L, TimeUnit.HOURS);
    }

    @EventHandler
//    @Override
    public void on(AdminRemovedEvent event, MetaData metaData) {
        repository.delete(event.getId());
    }

}
