/*
package com.ywy.learn.web.netty;

import com.alibaba.fastjson.JSON;
import com.ywy.learn.web.netty.coder.MessageConverter;
import com.ywy.learn.web.netty.message.AuctionDTO;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;
import org.msgpack.MessagePack;
import org.msgpack.type.ArrayValue;
import org.msgpack.type.Value;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.netty.handler.codec.http.HttpHeaders.isKeepAlive;
import static io.netty.handler.codec.http.HttpHeaders.setContentLength;
import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

*/
/**
 * @author ve
 * @date 2019/4/22 14:11
 * <p>
 * 处理MessagePack解码后的Value
 * @param value
 * @throws IOException
 * <p>
 * 处理HTTP请求，如果是websocket请求，构造握手响应
 * @param ctx
 * @param req
 * @throws Exception
 * <p>
 * 处理WebSocketFrame
 * @param ctx
 * @param frame
 * @throws IOException
 * <p>
 * 统一处理Event，为了让TCP和Websocket的处理逻辑统一，封装了该方法,两种协议的对象采用不同序列化方案
 * @param value
 * @param text
 * @throws IOException
 * <p>
 * 将指定的event发送给所有在线终端
 * @param testEvent
 * <p>
 * 获取channel的id
 * @param channel
 * @return 处理MessagePack解码后的Value
 * @param value
 * @throws IOException
 * <p>
 * 处理HTTP请求，如果是websocket请求，构造握手响应
 * @param ctx
 * @param req
 * @throws Exception
 * <p>
 * 处理WebSocketFrame
 * @param ctx
 * @param frame
 * @throws IOException
 * <p>
 * 统一处理Event，为了让TCP和Websocket的处理逻辑统一，封装了该方法,两种协议的对象采用不同序列化方案
 * @param value
 * @param text
 * @throws IOException
 * <p>
 * 将指定的event发送给所有在线终端
 * @param testEvent
 * <p>
 * 获取channel的id
 * @param channel
 * @return
 *//*

public class ServerChannelHandler extends SimpleChannelInboundHandler<Object> {

    // WebSocket Hand shaker
    private WebSocketServerHandshaker handshaker;

    @SuppressWarnings("deprecation")
    private static void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req, FullHttpResponse res) {
        // 返回应答给客户端
        if (res.getStatus().code() != 200) {
            ByteBuf buf = Unpooled.copiedBuffer(res.getStatus().toString(), CharsetUtil.UTF_8);
            res.content().writeBytes(buf);
            buf.release();
            setContentLength(res, res.content().readableBytes());
        }

        // 如果是非Keep-Alive，关闭连接
        ChannelFuture f = ctx.channel().writeAndFlush(res);
        if (!isKeepAlive(req) || res.getStatus().code() != 200) {
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }

    // 接收到消息
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, final Object object) throws Exception {
        // 管道读取到消息，先判断消息对象是什么类型，然后做不同处理
        try {
            if (object instanceof ArrayValue) {// 这是经过MessagePack解码完成后的对象
                handleValue(ctx, (ArrayValue) object);
            } else if (object instanceof FullHttpRequest) {// HTTP请求对象
                handleHttpRequest(ctx, (FullHttpRequest) object);
            } else if (object instanceof WebSocketFrame) {// WebSocket消息对象
                handleWebSocketFrame(ctx, (WebSocketFrame) object);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    */
/**
 * 处理MessagePack解码后的Value
 *
 * @param value
 * @throws IOException
 *//*

    private void handleValue(ChannelHandlerContext ctx, ArrayValue value) throws Exception {
        // 获取EventType,BaseEvent的第一个字段是EventType，这是约定好的
        handleEvent(ctx.channel().id().asLongText(), value, null);
    }

    */
/**
 * 处理HTTP请求，如果是websocket请求，构造握手响应
 *
 * @param ctx
 * @param req
 * @throws Exception
 *//*

    @SuppressWarnings("deprecation")
    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) throws Exception {
        // 如果HTTP解码失败，返回HHTP异常
        if (!req.getDecoderResult().isSuccess() || (!"websocket".equals(req.headers().get("Upgrade")))) {
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HTTP_1_1, BAD_REQUEST));
            return;
        }
        // 构造握手响应返回
        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory("ws://localhost:8080/websocket", null, false);
        handshaker = wsFactory.newHandshaker(req);
        if (handshaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedWebSocketVersionResponse(ctx.channel());
        } else {
            // 握手响应
            handshaker.handshake(ctx.channel(), req);
        }
    }

    */
/**
 * 处理WebSocketFrame
 *
 * @param ctx
 * @param frame
 * @throws IOException
 *//*

    private void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) throws IOException {
        // 判断是否是关闭链路的指令
        if (frame instanceof CloseWebSocketFrame) {
            handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
            return;
        }
        // 判断是否是Ping消息
        if (frame instanceof PingWebSocketFrame) {
            ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
            return;
        }
        // 判断是否是文本消息
        if (frame instanceof TextWebSocketFrame) {// 使用json编解码
            // 解析消息
            String text = ((TextWebSocketFrame) frame).text();
            // 获取eventType，与枚举EventType对应，这是约定好的
            String eventText = (String) JSON.parseObject(text).get("eventType");
            handleEvent(ctx.channel().id().asLongText(), null, text);
            return;
        }
        // 判断是否是二进制消息,还未测试,这段代码与本例无关，完全是用来提供参考...
        if (frame instanceof BinaryWebSocketFrame) {// 此处使用MessagePack编解码，未测试
            try {
                // 读取消息内容
                ByteBuf buffer = frame.content();
                // 解码,从数据包buffer中获取要操作的byte数组
                final int length = buffer.readableBytes();
                byte[] bytes = new byte[length];
                buffer.getBytes(buffer.readerIndex(), bytes, 0, length);
                // 反序列化，将bytes反序列化成对象
                MessagePack messagePack = new MessagePack();
                AuctionDTO testEvent = messagePack.read(bytes, AuctionDTO.class);
                System.out.println("testEvent:" + testEvent);

                // 将该消息回复给终端
                // 序列化
                messagePack = new MessagePack();
                bytes = messagePack.write(testEvent);
                // 编码
                buffer = Unpooled.buffer(128);
                BinaryWebSocketFrame bwsFrame = new BinaryWebSocketFrame(buffer);
                ctx.channel().write(bwsFrame);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    */
/**
 * 统一处理Event，为了让TCP和Websocket的处理逻辑统一，封装了该方法,两种协议的对象采用不同序列化方案
 *
 * @param value
 * @param text
 * @throws IOException
 *//*

    private void handleEvent(String channelId, Value value, String text) throws IOException {

        AuctionDTO auctionDTO = null;
        if (value != null) {// 如果是tcp协议，采用messagepack序列化
            auctionDTO = MessageConverter.converter(value, AuctionDTO.class);
        } else {// ws使用json序列化
            auctionDTO = JSON.parseObject(text, AuctionDTO.class);
        }
        System.out.println("收到新消息:" + auctionDTO);
        // 订阅竞拍的数据变更
        NettyServer.SUBSCRIBE.put(channelId, auctionDTO.getId());
        // 将该消息转发给所有终端
//        sendEventToAll(auctionDTO);

    }

    */
/**
 * 将指定的event发送给所有在线终端
 *
 * @param testEvent
 *//*

    private void sendEventToAll(AuctionDTO auctionDTO) {
        // 查出订阅的channel
        List<String> channelIds = new ArrayList<>();
        NettyServer.SUBSCRIBE.forEach((s, s2) -> {
            if (auctionDTO.getId().equals(s2)) {
                channelIds.add(s);
            }
        });
        // 将消息转发给所有在线的终端
        int count = 0;// 记录一共发送给多少个终端
        for (Map.Entry entry : NettyServer.CLIENTS.entrySet()) {
            if (!channelIds.contains(String.valueOf(entry.getKey()))) {
                continue;
            }
            ChannelWraper channelWraper = (ChannelWraper) entry.getValue();
            if (channelWraper != null && channelWraper.getChannel() != null && channelWraper.getChannel().isActive()) {
                if (channelWraper.getProtocol().equals(ChannelWraper.PROTOCOL_TCP)) {// tcp协议直接发送对象，用messagepack编解码
                    channelWraper.getChannel().writeAndFlush(auctionDTO);
                } else if (channelWraper.getProtocol().equals(ChannelWraper.PROTOCOL_WS)) {// ws协议发送json字符串
                    channelWraper.getChannel().writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(auctionDTO)));
                }
                count++;
            } else {
                NettyServer.CLIENTS.remove(getChannelId(channelWraper.getChannel()));
                System.out.println("channel error,remove:" + channelWraper);
            }
        }
        System.out.println("这条消息一共转发给" + count + "个终端");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
        ctx.flush();
        // System.out.println("channelReadComplete:" + ctx + "\n");
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        System.out.println("服务端 channelRegistered:" + ctx.channel());
        NettyServer.CLIENTS.put(getChannelId(ctx.channel()), new ChannelWraper(ctx.channel()));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("服务端 channelActive:" + ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("服务端 exceptionCaught:" + cause.getMessage());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        System.out.println("服务端 channelInactive :" + ctx.channel());
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
        System.out.println("服务端 channelUnregistered :" + ctx.channel());
        NettyServer.CLIENTS.remove(getChannelId(ctx.channel()));
    }

    */
/**
 * 获取channel的id
 *
 * @param channel
 * @return
 *//*

    private String getChannelId(Channel channel) {
        return channel.id().asLongText();
    }
}
*/
