package com.ywy.learn.command.user.api.event;

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
public class UserUpdatedEvent {

    private String id;
    private String email;

    private String certId;

    private String lastToken;
}
