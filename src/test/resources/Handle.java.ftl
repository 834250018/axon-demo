package com.ywy.learn.command.${aggregate};

import com.ywy.learn.command.${aggregate}.api.command.${aggregate?cap_first}CreateCommand;
import com.ywy.learn.command.${aggregate}.api.command.${aggregate?cap_first}RemoveCommand;
import com.ywy.learn.command.${aggregate}.api.command.${aggregate?cap_first}UpdateCommand;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.Aggregate;
import org.axonframework.commandhandling.model.Repository;
import org.axonframework.messaging.MetaData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
* @author ve
* @date 2019/3/29 15:30
*/
@Component
public class ${aggregate?cap_first}Handle implements IAdminCommand {

@Autowired
Repository<${aggregate?cap_first}> repository;

    @CommandHandler
    public void handle(${aggregate?cap_first}CreateCommand command, MetaData metaData) throws Exception {
    repository.newInstance(() -> new ${aggregate?cap_first}(command, metaData));
    }

    @Override
    @CommandHandler
    public void handle(${aggregate?cap_first}UpdateCommand command, MetaData metaData) {
    Aggregate<${aggregate?cap_first}> target = repository.load(command.getId());
        //        checkAuthorization(target,metaData);
        target.execute(aggregate -> aggregate.handle(command, metaData));
        }

    @Override
        @CommandHandler
        public void remove(${aggregate?cap_first}RemoveCommand command, MetaData metaData) {
        Aggregate<${aggregate?cap_first}> target = repository.load(command.getId());
            //        checkAuthorization(target,metaData);
            target.execute(aggregate -> aggregate.remove(command, metaData));
            }
            }
