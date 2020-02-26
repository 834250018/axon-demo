package com.ywy.learn.query.listener;

import com.alibaba.fastjson.JSON;
import com.ywy.learn.command.user.api.event.*;
import com.ywy.learn.query.entry.UserEntry;
import com.ywy.learn.query.repository.UserEntryRepository;
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
public class UserListener {

    @Autowired
    UserEntryRepository repository;

    @Autowired
    StringRedisTemplate redisTemplate;

    @EventHandler
    public void on(UserCreatedEvent event, MetaData metaData) {
        UserEntry userEntry = new UserEntry();
        userEntry.setId(event.getId());
        userEntry.setEmail(event.getEmail());
        userEntry.applyMetaData(metaData);
        repository.save(userEntry);
    }

    @EventHandler
//    @Override
    public void on(UserUpdatedEvent event, MetaData metaData) {
        UserEntry entry = repository.findOne(event.getId());
        BeanUtils.copyProperties(event, entry);
        repository.save(entry);
    }

    @EventHandler
//    @Override
    public void on(UserLoginedEvent event, MetaData metaData) {
        UserEntry entry = repository.findOne(event.getId());
        entry.setLastToken(event.getLastToken());
        repository.save(entry);
        redisTemplate.opsForValue().set(entry.getLastToken(), JSON.toJSONString(entry), 1L, TimeUnit.HOURS);
    }

    @EventHandler
//    @Override
    public void on(UserCertApplyedEvent event, MetaData metaData) {
        UserEntry entry = repository.findOne(event.getId());
        entry.setCertId(event.getCertId());
        repository.save(entry);
        redisTemplate.opsForValue().set(entry.getLastToken(), JSON.toJSONString(entry), 1L, TimeUnit.HOURS);
    }

    @EventHandler
//    @Override
    public void on(UserRemovedEvent event, MetaData metaData) {
        repository.delete(event.getId());
    }

}
