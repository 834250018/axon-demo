/*
package com.ywy.learn.web.netty;

import com.ywy.learn.web.netty.coder.MessagePackDecoder;
import com.ywy.learn.web.netty.coder.MessagePackEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

*/
/**
 * @author ve
 * @date 2019/4/19 15:11
 *//*

@Slf4j
public class NettyServer extends Thread {
    private final static NioEventLoopGroup boos = new NioEventLoopGroup(1);
    private final static NioEventLoopGroup worker = new NioEventLoopGroup();
    public static HashMap<String, ChannelWraper> CLIENTS = new HashMap<>();
    // 订阅,与客户端同时使用,key:channelId,value:竞拍id
    public static HashMap<String, String> SUBSCRIBE = new HashMap<>();
    ServerBootstrap serverBootstrap = new ServerBootstrap();
    private int port = 8009;

    protected static void shutdown() {
        boos.shutdownGracefully();
        worker.shutdownGracefully();
    }

    @Override
    public void run() {
        try {
            ChannelFuture f = serverBootstrap.group(boos, worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            // 协议解码处理器，判断是什么协议（WebSocket还是TcpSocket）,然后动态修改编解码器
                            pipeline.addLast("protocolHandler", new ProtocolDecoder());

                            */
/** TcpSocket协议需要使用的编解码器 *//*

                            // Tcp粘包处理，添加一个LengthFieldBasedFrameDecoder解码器，它会在解码时按照消息头的长度来进行解码。
                            pipeline.addLast("tcpFrameDecoder", new LengthFieldBasedFrameDecoder(65535, 0, 4, 0, 4));
                            // MessagePack解码器，消息进来后先由frameDecoder处理，再给msgPackDecoder处理
                            pipeline.addLast("tcpMsgPackDecoder", new MessagePackDecoder());
                            // Tcp粘包处理，添加一个
                            // LengthFieldPrepender编码器，它会在ByteBuf之前增加4个字节的字段，用于记录消息长度。
                            pipeline.addLast("tcpFrameEncoder", new LengthFieldPrepender(4));
                            // MessagePack编码器，消息发出之前先由frameEncoder处理，再给msgPackEncoder处理
                            pipeline.addLast("tcpMsgPackEncoder", new MessagePackEncoder());

                            */
/** WebSocket协议需要使用的编解码器 *//*

                            // websocket协议本身是基于http协议的，所以这边也要使用http解编码器
                            pipeline.addLast("httpCodec", new HttpServerCodec());
                            // netty是基于分段请求的，HttpObjectAggregator的作用是将请求分段再聚合,参数是聚合字节的最大长度
                            pipeline.addLast("httpAggregator", new HttpObjectAggregator(65536));
                            // 用于向客户端发送Html5文件，主要用于支持浏览器和服务端进行WebSocket通信
                            pipeline.addLast("httpChunked", new ChunkedWriteHandler());

                            // 管道消息处理
                            pipeline.addLast("channelHandler", new ServerChannelHandler());
                        }
                    })
                    .bind(port).sync();
            System.out.println("服务端已启动,随时欢迎骚扰\n");
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            shutdown();
        }
    }
}
*/
