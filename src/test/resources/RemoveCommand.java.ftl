package com.ywy.learn.command.${aggregate?uncap_first}.api.command;

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
public class ${aggregate?cap_first}RemoveCommand {
@TargetAggregateIdentifier
@NotBlank
private String id;
}
