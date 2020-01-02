package com.ywy.learn.web.controller.base;

import com.ywy.learn.infrastructure.gateway.MetaDataGateway;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.MetaData;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author ve
 * @date 2020/1/2 14:26
 */
@Slf4j
public class BaseController {

    @Autowired
    MetaDataGateway metaDataGateway;

    protected <T> T send(Object command, MetaData metaData) {
        return (T) metaDataGateway.send(command, metaData);
    }

    protected void sendAndWait(Object command, MetaData metaData) {
        try {
            metaDataGateway.sendAndWait(command, MetaData.emptyInstance());
        } catch (InterruptedException e) {
            log.error("command fail", e);
            Thread.currentThread().interrupt();
        }
    }
}
