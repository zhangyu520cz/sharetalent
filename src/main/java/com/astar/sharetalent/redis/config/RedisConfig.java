package com.astar.sharetalent.redis.config;

import com.alibaba.fastjson.JSON;
import com.astar.sharetalent.common.base.BaseDo;
import com.astar.sharetalent.common.constants.SystemConstants;
import com.astar.sharetalent.redis.serializer.ObjectSerializer;
import com.astar.sharetalent.redis.serializer.StringSerializer;
import com.xiaoleilu.hutool.util.ArrayUtil;
import com.xiaoleilu.hutool.util.StrUtil;
import org.springframework.cache.annotation.*;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author zhangyu
 * @description: Redis缓存配置类
 * @date 2017/11/24 10:19
 */
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

    /**
     * <p>
     * 自定义KEY生成器，格式： zjy:data:[包名 + 类名 + 方法名+ 参数]
     * 如：zjy:data:com.zjy.common.redis.RedisConfig:queryList_param1_param2
     * </p>
     */
    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                CacheConfig cacheConfig = target.getClass().getAnnotation(CacheConfig.class);
                Cacheable cacheable = method.getAnnotation(Cacheable.class);
                CachePut cachePut = method.getAnnotation(CachePut.class);
                CacheEvict cacheEvict = method.getAnnotation(CacheEvict.class);
                String cacheName = "";
                if (cacheable != null) {
                    String[] cacheNames = cacheable.value();
                    if (ArrayUtil.isNotEmpty(cacheNames)) {
                        cacheName = cacheNames[0];
                    }
                } else if (cachePut != null) {
                    String[] cacheNames = cachePut.value();
                    if (ArrayUtil.isNotEmpty(cacheNames)) {
                        cacheName = cacheNames[0];
                    }
                } else if (cacheEvict != null) {
                    String[] cacheNames = cacheEvict.value();
                    if (ArrayUtil.isNotEmpty(cacheNames)) {
                        cacheName = cacheNames[0];
                    }
                }
                if (cacheConfig != null && StrUtil.isBlank(cacheName)) {
                    String[] cacheNames = cacheConfig.cacheNames();
                    if (ArrayUtil.isNotEmpty(cacheNames)) {
                        cacheName = cacheNames[0];
                    }
                }
                if (StrUtil.isBlank(cacheName)) {
                    cacheName = "default";
                }
                String paramStr = getParamStr(params);
                StringBuilder sb = new StringBuilder();
                sb.append(SystemConstants.CACHE_DATA)
                        .append(cacheName).append(":")
                        .append(target.getClass().getName())
                        .append(":").append(method.getName())
                        .append("_").append(paramStr);
                return sb.toString();
            }

            /** 获取参数串（BaseModel取ID），以下划线连线 */
            private String getParamStr(Object[] params) {
                if (ArrayUtil.isEmpty(params)) {
                    return "";
                }
                String paramStr = "";
                for (Object param : params) {
                    //BaseModel类型，取ID
                    if (param instanceof BaseDo) {
                        BaseDo model = (BaseDo) param;
                        paramStr = String.join("_", paramStr, String.valueOf(model.getId()));
                    } else if (ArrayUtil.isArray(param)) {
                        //数组类型，将各元素序列化为字符串
                        Object[] arrs = (Object[]) param;
                        paramStr = Arrays.stream(arrs).map(JSON::toJSONString).collect(Collectors.joining("_"));
                    } else {
                        //其它类型，直接序列化为字符串
                        paramStr = String.join("_", paramStr, JSON.toJSONString(param));
                    }
                }
                return paramStr;
            }
        };
    }


    /**
     * RedisTemplate序列化方式设置，并初始化
     */
    @Bean
    public RedisTemplate<String, Serializable> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Serializable> template = new RedisTemplate<String, Serializable>();
        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringSerializer());
        template.setValueSerializer(new ObjectSerializer());
        template.afterPropertiesSet();
        return template;
    }


}
