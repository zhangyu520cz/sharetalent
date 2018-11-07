package com.astar.sharetalent.config;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * @author zhangyu
 * @description FastJson配置
 * @date 2018/10/12 18:29
 */
@Configuration
public class FastJsonConfiguration {

    @Bean
    public HttpMessageConverters fastJsonHttpMessageConverters(){
        FastJsonHttpMessageConverter fastConverter=new FastJsonHttpMessageConverter();
        //添加fastjson的配置信息，比如是否要格式化返回的json数据
        com.alibaba.fastjson.support.config.FastJsonConfig fastJsonConfig=new com.alibaba.fastjson.support.config.FastJsonConfig();
        //SerializerFeature属性
        //https://blog.csdn.net/u010246789/article/details/52539576
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat,SerializerFeature.WriteMapNullValue);

        SerializeConfig serializeConfig = new SerializeConfig();
        serializeConfig.put(BigInteger.class, ToStringSerializer.instance);
        serializeConfig.put(Long.class,ToStringSerializer.instance);
        serializeConfig.put(Long.TYPE,ToStringSerializer.instance);
        fastJsonConfig.setSerializeConfig(serializeConfig);

        fastConverter.setFastJsonConfig(fastJsonConfig);
        HttpMessageConverter<?> converter=fastConverter;
        return new HttpMessageConverters(converter);
    }

    @Bean
    public StringHttpMessageConverter stringHttpMessageConverter(){
        StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter();
        stringHttpMessageConverter.setDefaultCharset(Charset.forName("UTF-8"));
        stringHttpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON_UTF8,
                new MediaType("application", "*+json", Charset.forName("UTF-8"))));
        return stringHttpMessageConverter;
    }
}
