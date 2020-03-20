package com.ywy.learn.common.api.base;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.MetaData;

/**
 * @author ve
 * @date 2020/3/20 15:40
 */
@Slf4j
public class BaseHandle {
    protected void checkAuthorization(Object o, MetaData metaData) {
        log.info("检查权限", o, metaData);
    }
}
