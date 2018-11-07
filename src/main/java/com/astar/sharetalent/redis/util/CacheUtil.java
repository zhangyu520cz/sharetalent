package com.astar.sharetalent.redis.util;

import com.astar.sharetalent.common.constants.SystemConstants;
import com.xiaoleilu.hutool.util.ArrayUtil;
import com.xiaoleilu.hutool.util.StrUtil;
import org.springframework.cache.annotation.CacheConfig;

/**
 * 缓存工具类
 */
public class CacheUtil {

    private static CacheManager cacheManager;

    public static void setCacheManager(CacheManager cacheManager) {
        CacheUtil.cacheManager = cacheManager;
    }

    public static CacheManager getCache() {
        return cacheManager;
    }

    /**
     * 获取缓存键值
     */
    public static String getCacheKey(Object id, Class<?> cls) {
        String cacheName = getCacheKey(cls);
        return new StringBuilder(SystemConstants.CACHE_DATA).append(cacheName).append(":").append(id).toString();
    }

    /**
     * 获取锁键值
     */
    public static String getLockKey(Object id, Class<?> cls) {
        String cacheName = getCacheKey(cls);
        return new StringBuilder(SystemConstants.CACHE_LOCK).append(cacheName).append(":").append(id).toString();
    }



    public static String getCacheKey(Class<?> cls) {
        String cacheName = SystemConstants.CACHE_KEY_MAP.get(cls);
        if (StrUtil.isBlank(cacheName)) {
            CacheConfig cacheConfig = cls.getAnnotation(CacheConfig.class);
            if (cacheConfig != null && ArrayUtil.isNotEmpty(cacheConfig.cacheNames())) {
                cacheName = cacheConfig.cacheNames()[0];
            } else {
                cacheName = cls.getName();
            }
            SystemConstants.CACHE_KEY_MAP.put(cls, cacheName);
        }
        return cacheName;
    }
}