package com.ywy.learn.command.user.api.command;

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
public class UserUpdateCommand {
    @TargetAggregateIdentifier
    @ApiModelProperty(value = "id", required = true)
    @NotBlank
    private String id;

    private String email;

    @ApiModelProperty(value = "证书id")
    private String certId;

    @ApiModelProperty(value = "最后一次登录token")
    private String lastToken;
}
