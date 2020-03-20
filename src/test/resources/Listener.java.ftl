package com.ywy.learn.query.listener;

import com.ywy.learn.command.${aggregate?uncap_first}.api.event.${aggregate?cap_first}CreatedEvent;
import com.ywy.learn.command.${aggregate?uncap_first}.api.event.${aggregate?cap_first}RemovedEvent;
import com.ywy.learn.command.${aggregate?uncap_first}.api.event.${aggregate?cap_first}UpdatedEvent;
import com.ywy.learn.query.entry.${aggregate?cap_first}Entry;
import com.ywy.learn.query.repository.${aggregate?cap_first}EntryRepository;
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
public class ${aggregate?cap_first}Listener implements IAdminEvent {

    @Autowired
${aggregate?cap_first}EntryRepository repository;

    @Override
    @EventHandler
    public void on(${aggregate?cap_first}CreatedEvent event, MetaData metaData) {
${aggregate?cap_first}Entry entry = new ${aggregate?cap_first}Entry();
        BeanUtils.copyProperties(event, entry);
        entry.applyMetaData(metaData);
        repository.save(entry);
    }

    @Override
    @EventHandler
    public void on(${aggregate?cap_first}UpdatedEvent event, MetaData metaData) {
${aggregate?cap_first}Entry entry = repository.findOne(event.getId());
        BeanUtils.copyProperties(event, entry);
        repository.save(entry);
    }

    @Override
    @EventHandler
    public void on(${aggregate?cap_first}RemovedEvent event, MetaData metaData) {
        repository.delete(event.getId());
    }

}
