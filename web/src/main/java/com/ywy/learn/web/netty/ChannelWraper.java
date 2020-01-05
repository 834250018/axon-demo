/*
package com.ywy.learn.web.netty;

import com.alibaba.fastjson.JSON;
import io.netty.channel.Channel;

*/
/**
 * @author ve
 * @date 2019/4/22 14:07
 * <p>
 * tcp协议
 * <p>
 * websocket协议
 * <p>
 * tcp协议
 * <p>
 * websocket协议
 * <p>
 * tcp协议
 * <p>
 * websocket协议
 *//*

public class ChannelWraper {

    */
/**
 * tcp协议
 *//*

    public static final String PROTOCOL_TCP = "TCP";
    */
/**
 * websocket协议
 *//*

    public static final String PROTOCOL_WS = "WS";

    // 通信管道
    private Channel channel;
    // 通信协议，取值为
    private String protocol;

    public ChannelWraper(Channel channel) {
        setChannel(channel);
        setProtocol(PROTOCOL_TCP);
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
*/
