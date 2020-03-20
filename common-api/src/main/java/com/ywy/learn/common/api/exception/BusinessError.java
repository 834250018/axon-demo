package com.ywy.learn.common.api.exception;

/**
 * 第1位为级别(1为http模块,请求异常等;2为业务模块)
 * 2~3位为服务模块
 * 4~6位为自增的错误编码
 *
 * @author ve
 * @date 2018/7/25 15:59
 */
public interface BusinessError {

    BusinessErrorCode BU_4004 = new BusinessErrorCode("4004", "uri不存在");
    BusinessErrorCode BU_5001 = new BusinessErrorCode("404", "线程超时");

    BusinessErrorCode BU_9000 = new BusinessErrorCode("9000", "请刷新重试");
    BusinessErrorCode BU_9001 = new BusinessErrorCode("9001", "登录已过期");

    // 公共部分
    BusinessErrorCode BU_9200 = new BusinessErrorCode("9200", "邮件发送失败");
    BusinessErrorCode BU_9201 = new BusinessErrorCode("9201", "验证码错误");
    BusinessErrorCode BU_9202 = new BusinessErrorCode("9202", "验证失败");


    // admin 部分
    BusinessErrorCode BU_9400 = new BusinessErrorCode("9400", "");

    // user 部分
    BusinessErrorCode BU_9600 = new BusinessErrorCode("9600", "用户不存在");
    BusinessErrorCode BU_9601 = new BusinessErrorCode("9601", "");


}