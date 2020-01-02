package com.ywy.learn.web.netty.coder;

import org.msgpack.MessagePack;
import org.msgpack.type.Value;
import org.msgpack.unpacker.Converter;

import java.io.IOException;

/**
 * message 转换类
 *
 * @author lucher
 */
public class MessageConverter {

    /**
     * 将value转为指定的实体类
     *
     * @param value
     * @param cls
     * @return
     * @throws IOException
     */
    public static <T> T converter(Value value, Class<T> cls) throws IOException {
        Converter converter = new Converter(new MessagePack(), value);
        T obj = null;
        try {
            obj = converter.read(cls);
        } finally {
            converter.close();// 关闭coverter
            converter = null;
        }
        return obj;
    }
}
