package com.ywy.learn.web.advice;

import com.ywy.learn.infrastructure.exception.BusinessException;
import com.ywy.learn.web.pojo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseVO handle(Exception e) {
        log.error(e.getMessage(), e);
        return new ResponseVO(HttpStatus.BAD_REQUEST.value(), null);
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public ResponseVO handle(BusinessException e) {
        String str[] = e.getMessage().split("#");
        log.error(str[1], e);
        return new ResponseVO(Integer.valueOf(str[0]), str[1]);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseBody
    public ResponseVO handle(MethodArgumentNotValidException e) {
        return new ResponseVO(HttpStatus.BAD_REQUEST.value(), "非法参数" + e.getMessage());
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    @ResponseBody
    public ResponseVO handle(ConstraintViolationException e) {
        return new ResponseVO(HttpStatus.BAD_REQUEST.value(), "非法参数" + e.getMessage());
    }

    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    @ResponseBody
    public ResponseVO handle(HttpMessageNotReadableException e) {
        return new ResponseVO(HttpStatus.BAD_REQUEST.value(), "非法参数" + e.getMessage());
    }
}
