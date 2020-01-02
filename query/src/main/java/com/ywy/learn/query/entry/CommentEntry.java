package com.ywy.learn.query.entry;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * @author ve
 * @date 2019/3/29 9:11
 */
@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants
public class CommentEntry implements Serializable {

    public CommentEntry(String commentId) {
        this.commentId = commentId;
    }

    @Id
    private String commentId;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "文章id")
    private String articleId;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "发布时间")
    private Long publicTime;
}
