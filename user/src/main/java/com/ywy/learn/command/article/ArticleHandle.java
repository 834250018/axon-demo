package com.ywy.learn.command.article;

import com.ywy.learn.command.article.api.command.ArticleCreateCommand;
import com.ywy.learn.command.article.api.command.ArticleRemoveCommand;
import com.ywy.learn.command.article.api.command.ArticleUpdateCommand;
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
public class ArticleHandle {

@Autowired
Repository<Article> repository;

    @CommandHandler
    public void handle(ArticleCreateCommand command, MetaData metaData) throws Exception {
    repository.newInstance(() -> new Article(command, metaData));
    }

    @CommandHandler
    public void handle(ArticleUpdateCommand command, MetaData metaData) {
    Aggregate<Article> target = repository.load(command.getId());
        //        checkAuthorization(target,metaData);
        target.execute(aggregate -> aggregate.update(command, metaData));
        }

        @CommandHandler
        public void handle(ArticleRemoveCommand command, MetaData metaData) {
        Aggregate<Article> target = repository.load(command.getId());
            //        checkAuthorization(target,metaData);
            target.execute(aggregate -> aggregate.remove(command, metaData));
            }
            }
