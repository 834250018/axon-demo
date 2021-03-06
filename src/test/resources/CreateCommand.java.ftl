package com.ywy.learn.command.${aggregate?uncap_first}.api.command;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.TargetAggregateIdentifier;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
* @author ve
* @date 2019/3/29 15:35
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ${aggregate?cap_first}CreateCommand {

@TargetAggregateIdentifier
@ApiModelProperty(value = "id", hidden = true)
private String id;

@ApiModelProperty(value = "姓名", required = true)
@NotBlank
private String name;

@ApiModelProperty(value = "年龄", required = true)
@NotNull
private Integer age;
}
