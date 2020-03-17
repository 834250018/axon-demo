package com.ywy.learn.web.controller;

import com.ywy.learn.infrastructure.exception.BusinessException;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ve
 * @date 2019/3/27 16:51
 */
@RestController
public class NotFoundController implements ErrorController {

    @Override
    @RequestMapping
    public String getErrorPath() {
        throw new BusinessException("uri不存在");
    }
}
