package com.ywy.learn.command.article.api.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author ve
 * @date 2019/3/29 20:17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleCommentedEvent {

    private String id;

    private List<String> comments;

}
