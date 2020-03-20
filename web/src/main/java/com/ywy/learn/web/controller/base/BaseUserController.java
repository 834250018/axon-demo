package com.ywy.learn.web.controller.base;

import com.alibaba.fastjson.JSON;
import com.ywy.learn.common.api.exception.BusinessError;
import com.ywy.learn.common.api.exception.BusinessException;
import com.ywy.learn.query.entry.UserEntry;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.axonframework.messaging.MetaData;

/**
 * @author ve
 * @date 2020/1/2 14:26
 */
@Slf4j
public class BaseUserController extends BaseController {

    protected String getUserId() {
        return getUser().getId();
    }

    protected String getToken() {
        String token = request.getHeader("U-Token");
        if (StringUtils.isBlank(token)) {
            throw new BusinessException(BusinessError.BU_9001);
        }
        return token;
    }

    protected UserEntry getUser() {
        String user = redisTemplate.opsForValue().get(getToken());
        if (StringUtils.isBlank(user)) {
            throw new BusinessException(BusinessError.BU_9001);
        }
        return JSON.parseObject(user, UserEntry.class);
    }

    protected MetaData genMetaData() {
        return MetaData.with("operationTime", System.currentTimeMillis());
    }

    @Override
    protected <T> T send(Object command) {
        return (T) metaDataGateway.send(command, genMetaData());
    }


    @Override
    protected <R> R sendAndWait(Object command) {
        try {
            return metaDataGateway.sendAndWait(command, genMetaData());
        } catch (InterruptedException e) {
            log.error("command fail", e);
            Thread.currentThread().interrupt();
            throw new BusinessException(BusinessError.BU_5001);
        }
    }

}
