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
public class ${aggregate?cap_first}Entry extends BaseEntry {

    @Id
    private String id;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "年龄")
    private Integer age;
}
