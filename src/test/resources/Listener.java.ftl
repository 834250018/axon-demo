package com.ywy.learn.query.Listener;

import com.ywy.learn.command.${aggregate?uncap_first}.api.event.${aggregate}CreatedEvent;
import com.ywy.learn.command.${aggregate?uncap_first}.api.event.${aggregate}RemovedEvent;
import com.ywy.learn.command.${aggregate?uncap_first}.api.event.${aggregate}UpdatedEvent;
import com.ywy.learn.query.entry.${aggregate}Entry;
import com.ywy.learn.query.repository.${aggregate}EntryRepository;
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
public class ${aggregate}Listener {

    @Autowired
    ${aggregate}EntryRepository repository;

    @EventHandler
    public void on(${aggregate}CreatedEvent event, MetaData metaData) {
        ${aggregate}Entry ${aggregate?uncap_first}Entry = new ${aggregate}Entry();
        BeanUtils.copyProperties(event, ${aggregate?uncap_first}Entry);
        repository.save(${aggregate?uncap_first}Entry);
    }

    @EventHandler
//    @Override
    public void on(${aggregate}UpdatedEvent event, MetaData metaData) {
        ${aggregate}Entry entry = repository.findOne(event.get${aggregate}Id());
        BeanUtils.copyProperties(event, entry);
        repository.save(entry);
    }

    @EventHandler
//    @Override
    public void on(${aggregate}RemovedEvent event, MetaData metaData) {
        repository.delete(event.getId());
    }

}
