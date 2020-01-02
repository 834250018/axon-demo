package com.ywy.learn.command.article.api.command;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.TargetAggregateIdentifier;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Null;

/**
 * @author ve
 * @date 2019/3/29 15:35
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleCommentCommand {
    @TargetAggregateIdentifier
    @ApiModelProperty(value = "id", required = true)
    @NotBlank
    private String articleId;

    @ApiModelProperty(value = "内容", required = true)
    @NotBlank
    private String content;

    @ApiModelProperty(value = "评论id", hidden = true)
    @Null
    private String commentId;
}
