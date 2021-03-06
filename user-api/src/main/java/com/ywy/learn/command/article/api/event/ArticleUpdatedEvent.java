package com.ywy.learn.command.article.api.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ve
 * @date 2019/3/29 20:17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleUpdatedEvent {

    private String id;

    private String title;

    private String content;
}
