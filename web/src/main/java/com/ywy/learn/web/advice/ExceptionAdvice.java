package com.ywy.learn.web.advice;

import com.ywy.learn.infrastructure.exception.BusinessException;
import com.ywy.learn.web.pojo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

/**
 * 异常切面,restful接口异常不记录,程序内业务异常记录
 *
 * @author ve
 * @date 2019/7/2 18:10
 */
@ControllerAdvice
@Slf4j
public class ExceptionAdvice {

    @ExceptionHandler()
    @ResponseBody
    public ResponseVO handleRuntimeException(RuntimeException e, HttpServletRequest request, HttpServletResponse response) {
        log.error(e.getMessage(), e);
        return new ResponseVO(HttpStatus.BAD_REQUEST.toString(), null);
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public ResponseVO handleYnacErrorException(BusinessException e, HttpServletRequest request, HttpServletResponse response) {
        String str[] = e.getMessage().split("#");
        log.error(str[1], e);
        return new ResponseVO(str[0], str[1]);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseBody
    public ResponseVO handleYnacErrorException(MethodArgumentNotValidException e, HttpServletRequest request, HttpServletResponse response) {
        return new ResponseVO(HttpStatus.BAD_REQUEST.toString(), "非法参数");
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    @ResponseBody
    public ResponseVO handleYnacErrorException(ConstraintViolationException e, HttpServletRequest request, HttpServletResponse response) {
        return new ResponseVO(HttpStatus.BAD_REQUEST.toString(), "非法参数");
    }
}
