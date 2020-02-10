package com.swpu.sharebook.shiro.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.cache.CacheManager;

/**
 * @author KeennessNewBie
 * @Date 2020/1/16 21:12
 * @Message
 */
public class ShiroCacheManager implements org.apache.shiro.cache.CacheManager {
    private CacheManager springCacheManager;

    public ShiroCacheManager(CacheManager springCacheManager) {
        super();
        this.springCacheManager = springCacheManager;
    }

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        return new ShiroCache<K, V>(springCacheManager, name);
    }
}
