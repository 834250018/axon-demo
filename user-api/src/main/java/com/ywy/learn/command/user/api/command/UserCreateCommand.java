package com.ywy.learn.command.user.api.command;

import io.swagger.annotations.ApiModelProperty;
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
public class UserCreateCommand {

    @TargetAggregateIdentifier
    @ApiModelProperty(value = "id", hidden = true)
    private String id;

    @ApiModelProperty(value = "姓名", required = true)
    private String email;
}
