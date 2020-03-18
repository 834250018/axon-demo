package com.ywy.learn.web.controller;

import com.ywy.learn.infrastructure.exception.BusinessException;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ve
 * @date 2019/3/27 16:51
 */
@Controller
@ApiIgnore
public class NotFoundController implements ErrorController {

    @Override
    public String getErrorPath() {
        return "error";
    }

    @RequestMapping("error")
    public String handleError(HttpServletRequest request) {
        throw new BusinessException("uri不存在");
    }

}
