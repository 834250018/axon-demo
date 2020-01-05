package com.ywy.learn.command.admin.api.event;

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
public class AdminCreatedEvent {

    private String id;

    private String username;

    // sha256(sha256(用户密码), salt)
    private String password;

    private String salt;
}
