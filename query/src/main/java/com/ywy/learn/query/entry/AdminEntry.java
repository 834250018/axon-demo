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
public class AdminEntry extends BaseEntry {

    @Id
    private String id;

    @ApiModelProperty(value = "用户名")
    private String username;

//    @ApiModelProperty(value = "密码")
    // sha256(sha256(用户密码), salt)
//    private String password;

//    @ApiModelProperty(value = "盐")
//    private String salt;

    private String lastToken;
}
