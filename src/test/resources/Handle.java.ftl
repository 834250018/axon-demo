package com.ywy.learn.command.${aggregate};

import com.ywy.learn.test.result.${module}.api.command.${aggregate}CreateCommand;
import com.ywy.learn.test.result.${module}.api.command.${aggregate}RemoveCommand;
import com.ywy.learn.test.result.${module}.api.command.${aggregate}UpdateCommand;
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
public class ${aggregate}Handle {

@Autowired
Repository<${aggregate}> repository;

    @CommandHandler
    public void handle(${aggregate}CreateCommand command, MetaData metaData) throws Exception {
    repository.newInstance(() -> new ${aggregate}(command, metaData));
    }

    @CommandHandler
    public void handle(${aggregate}UpdateCommand command, MetaData metaData) {
    Aggregate<${aggregate}> target = repository.load(command.get${aggregate}Id());
        //        checkAuthorization(target,metaData);
        target.execute(aggregate -> aggregate.update(command, metaData));
        }

        @CommandHandler
        public void handle(${aggregate}RemoveCommand command, MetaData metaData) {
        Aggregate<${aggregate}> target = repository.load(command.get${aggregate}Id());
            //        checkAuthorization(target,metaData);
            target.execute(aggregate -> aggregate.remove(command, metaData));
            }
            }
