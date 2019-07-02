package com.ywy.learn.infrastructure.exception;

/**
 * 第1位为级别(1为http模块,请求异常等;2为业务模块)
 * 2~3位为服务模块
 * 4~6位为自增的错误编码
 *
 * @author ve
 * @date 2018/7/25 15:59
 */
public interface BusinessError {

    BusinessErrorCode BU_000000 = new BusinessErrorCode("000000", "请刷新重试");

}