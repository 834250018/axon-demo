package com.ywy.learn.web.pojo;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * @author ve
 * @date 2019/7/2 18:06
 */
@Data
public class ResponseVO implements Serializable {

    /**
     * 编码
     */
    private int code;

    /**
     * 返回数据
     */
    private Object data;

    /**
     * 服务器时间
     */
    private Long serverTime;

    /**
     * 消息
     */
    private String msg;

    public ResponseVO(Object data) {
        this.code = HttpStatus.OK.value();
        this.data = data;
        this.msg = null;
        this.serverTime = System.currentTimeMillis();
    }

    public ResponseVO(int code, String msg) {
        this.code = code;
        this.data = null;
        this.msg = msg;
        this.serverTime = System.currentTimeMillis();
    }
}
