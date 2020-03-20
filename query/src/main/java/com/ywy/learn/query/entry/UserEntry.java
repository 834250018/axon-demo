package com.ywy.learn.query.entry;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author ve
 * @date 2019/3/29 9:11
 */
@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants
public class UserEntry extends BaseEntry {

    @Id
    private String id;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "证书id")
    private String certId;

    @ApiModelProperty(value = "最后一次登录token")
    private String lastToken;
}
