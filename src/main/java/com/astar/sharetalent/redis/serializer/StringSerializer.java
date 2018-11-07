package com.astar.sharetalent.redis.serializer;


import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.io.UnsupportedEncodingException;


/**
 * redis key序列化方式
 * @author  zhangyu
 * @date 2018/6/25
 */

@Slf4j
public class StringSerializer implements RedisSerializer<String> {

    private static final String DEFAULT_CHARSET = "UTF-8";

    /**
     * Refer to https://docs.oracle.com/javase/8/docs/technotes/guides/intl/encoding.doc.html
     * UTF-8, UTF-16, UTF-32, ISO-8859-1, GBK, Big5, etc
     */
    private String charset = DEFAULT_CHARSET;

    @Override
    public byte[] serialize(String s) throws SerializationException {
        try {
            return (s == null ? null : s.getBytes(charset));
        } catch (UnsupportedEncodingException e) {
            throw new SerializationException("serialize error, string=" + s, e);
        }
    }

    @Override
    public String deserialize(byte[] bytes) throws SerializationException {
        try {
            return (bytes == null ? null : new String(bytes, charset));
        } catch (UnsupportedEncodingException e) {
            throw new SerializationException("deserialize error", e);
        }
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }
}
