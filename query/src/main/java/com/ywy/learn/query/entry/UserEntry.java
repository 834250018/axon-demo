package com.ywy.learn.query.entry;

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
public class UserEntry implements Serializable {

    @Id
    private String userId;
    private String name;
    private String age;
}
