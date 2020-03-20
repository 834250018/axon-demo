package com.ywy.learn.web.controller.base;

import com.alibaba.fastjson.JSON;
import com.ywy.learn.common.api.exception.BusinessError;
import com.ywy.learn.common.api.exception.BusinessException;
import com.ywy.learn.query.entry.AdminEntry;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.axonframework.messaging.MetaData;

/**
 * @author ve
 * @date 2020/1/2 14:26
 */
@Slf4j
public class BaseAdminController extends BaseController {

    protected String getAdminId() {
        return getAdmin().getId();
    }

    protected AdminEntry getAdmin() {
        String admin = redisTemplate.opsForValue().get(getToken());
        if (StringUtils.isBlank(admin)) {
            throw new BusinessException(BusinessError.BU_9001);
        }
        return JSON.parseObject(admin, AdminEntry.class);
    }

    protected MetaData genMetaData() {
        return MetaData.with("adminId", getAdminId()).and("operationTime", System.currentTimeMillis());
    }

    @Override
    protected <T> T send(Object command) {
        return (T) metaDataGateway.send(command, genMetaData());
    }


    protected String getToken() {
        String token = request.getHeader("X-Token");
        if (StringUtils.isBlank(token)) {
            throw new BusinessException(BusinessError.BU_9001);
        }
        return token;
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
