package com.ywy.learn.command.article.api.command;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.TargetAggregateIdentifier;
import org.hibernate.validator.constraints.NotBlank;

/**
* @author ve
* @date 2019/3/29 15:35
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleUpdateCommand {
@TargetAggregateIdentifier
@ApiModelProperty(value = "id", required = true)
@NotBlank
private String id;

@ApiModelProperty(value = "姓名")
private String name;

@ApiModelProperty(value = "年龄")
private Integer age;
}
