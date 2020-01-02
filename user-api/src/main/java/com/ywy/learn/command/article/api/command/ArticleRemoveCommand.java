package com.ywy.learn.command.article.api.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

/**
* @author ve
* @date 2019/3/29 15:35
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleRemoveCommand {
@TargetAggregateIdentifier
private String id;
}
