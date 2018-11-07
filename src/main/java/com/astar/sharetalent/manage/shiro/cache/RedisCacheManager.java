package com.astar.sharetalent.manage.shiro.cache;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Shiro缓存管理器（Redis实现）
 *
 * @author jujun chen
 * @date 2017/12/29 18:21
 */
@Slf4j
@Component
public class RedisCacheManager implements CacheManager {

    @Value("${shiro.session.timeout:30}")
    private int sessionTimeOut;

    private static final ConcurrentMap<String, Cache> caches = new ConcurrentHashMap();

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        log.debug("获取名称为: " + name + " 的RedisCache实例");
        Cache<K, V> cache = caches.get(name);
        if (cache == null) {
            cache =  new RedisCache<K, V>(sessionTimeOut);
            caches.put(name,cache);
        }
        return cache;
    }
}
