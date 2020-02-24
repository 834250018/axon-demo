package com.ywy.learn.web.controller.base;

import com.ywy.learn.infrastructure.exception.BusinessException;
import com.ywy.learn.infrastructure.gateway.MetaDataGateway;
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
public class AdminController extends BaseController {

    @Autowired
    MetaDataGateway metaDataGateway;

    @Autowired
    HttpServletRequest request;

    @Autowired
    StringRedisTemplate redisTemplate;

    protected MetaData genAdminMetaData() {
        return MetaData.with("adminId", getAdminId()).and("operationTime", System.currentTimeMillis());
    }

    protected <T> T send(Object command) {
        return (T) metaDataGateway.send(command, genAdminMetaData());
    }


    protected <R> R sendAndWait(Object command) {
        try {
            return metaDataGateway.sendAndWait(command, genAdminMetaData());
        } catch (InterruptedException e) {
            log.error("command fail", e);
            throw new BusinessException("线程堵塞");
//            Thread.currentThread().interrupt();
        }
    }

}