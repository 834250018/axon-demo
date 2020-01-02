package com.ywy.learn.web.vo;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * @author ve
 * @date 2019/7/2 18:06
 */
@Data
public class ResponseVO<T> implements Serializable {

    /**
     * 编码
     */
    private String code;

    /**
     * 返回数据
     */
    private T data;

    /**
     * 服务器时间
     */
    private Long serverTime;

    /**
     * 消息
     */
    private String msg;

    public ResponseVO(T data) {
        this.code = String.valueOf(HttpStatus.OK.value());
        this.data = data;
        this.serverTime = System.currentTimeMillis();
    }

    public ResponseVO(String code, String msg) {
        this.code = code;
        this.msg = msg;
        this.serverTime = System.currentTimeMillis();
    }
}
