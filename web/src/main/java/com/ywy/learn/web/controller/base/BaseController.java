package com.ywy.learn.web.controller.base;

import com.alibaba.fastjson.JSON;
import com.ywy.learn.infrastructure.exception.BusinessException;
import com.ywy.learn.infrastructure.gateway.MetaDataGateway;
import com.ywy.learn.query.entry.AdminEntry;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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

    protected <T> T send(Object command, MetaData metaData) {
        return (T) metaDataGateway.send(command, metaData);
    }

    protected <R> R sendAndWait(Object command, MetaData metaData) {
        try {
            return metaDataGateway.sendAndWait(command, MetaData.emptyInstance());
        } catch (InterruptedException e) {
            log.error("command fail", e);
            throw new BusinessException("线程堵塞");
//            Thread.currentThread().interrupt();
        }
    }

    protected String getAdminId() {
        return getAdmin().getId();
    }

    protected String getToken() {
        String token = request.getHeader("X-Token");
        if (StringUtils.isBlank(token)) {
            throw new BusinessException("登录已过期");
        }
        return token;
    }

    protected AdminEntry getAdmin() {
        String admin = redisTemplate.opsForValue().get(getToken());
        if (StringUtils.isBlank(admin)) {
            throw new BusinessException("登录已过期");
        }
        return JSON.parseObject(admin, AdminEntry.class);
    }
}
