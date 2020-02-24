package com.ywy.learn.query.entry;

import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.axonframework.messaging.MetaData;

import java.io.Serializable;

/**
 * @author ve
 * @date 2020/2/24 13:15
 */
@Data
@FieldNameConstants
public class BaseEntry implements Serializable {

    private String adminId;
    private String userId;

    public void applyMetaData(MetaData metaData) {
        adminId = String.valueOf(metaData.get(BaseEntry.FIELD_ADMIN_ID));
        userId = String.valueOf(metaData.get(BaseEntry.FIELD_USER_ID));

    }
}
