package com.ywy.learn.web.controller.base;

import com.ywy.learn.common.api.exception.BusinessError;
import com.ywy.learn.common.api.exception.BusinessException;
import com.ywy.learn.common.api.gateway.MetaDataGateway;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.MetaData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ve
 * @date 2020/1/2 14:26
 */
@Slf4j
public class BaseController {

    @Autowired
    MetaDataGateway metaDataGateway;

    @Autowired
    HttpServletRequest request;

    @Autowired
    StringRedisTemplate redisTemplate;

    protected <T> T send(Object command) {
        return (T) metaDataGateway.send(command, MetaData.emptyInstance());
    }

    protected <R> R sendAndWait(Object command) {
        try {
            return metaDataGateway.sendAndWait(command, MetaData.emptyInstance());
        } catch (InterruptedException e) {
            log.error("command fail", e);
            Thread.currentThread().interrupt();
            throw new BusinessException(BusinessError.BU_5001);
        }
    }
}
