package com.ywy.learn.command.admin.api.command;

import com.ywy.learn.common.api.base.BaseCommand;
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
public class AdminCreateCommand implements BaseCommand {

    @TargetAggregateIdentifier
    @ApiModelProperty(value = "id", hidden = true)
    private String id;

    @ApiModelProperty(value = "用户名", required = true)
    @NotBlank
    private String username;

    @ApiModelProperty(value = "密码", required = true)
    @NotBlank
    // sha256(sha256(用户密码), salt)
    private String password;


}
