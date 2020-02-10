package com.swpu.sharebook.shiro.cache;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.CacheException;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author KeennessNewBie
 * @Date 2020/1/16 21:10
 * @Message 自定义shiro缓存实现 shiro的 Cache接口
 */
@Slf4j
public class ShiroCache<K, V> implements org.apache.shiro.cache.Cache<K, V> {


    private CacheManager springCacheManager;

    private String cacheName;

    private Cache springCache;

    public ShiroCache(CacheManager springCacheManager, String name) {
        this.springCacheManager = springCacheManager;
        this.cacheName = name;
        this.springCache = springCacheManager.getCache(name);

    }

    @SuppressWarnings("unchecked")
    @Override
    public V get(K key) throws CacheException {
        log.info("ShiroCache.get(" + key + ") " + cacheName);
        ValueWrapper valueWrapper = springCache.get(key);

        if (null == valueWrapper) {
            return null;
        }
        return (V) valueWrapper.get();
    }

    @Override
    public V put(K key, V value) throws CacheException {
        log.info("ShiroCache.put(" + cacheName + "_" + key + "_" + value + ")");
        springCache.put(key, value);
        return value;
    }

    @Override
    public V remove(K key) throws CacheException {
        V v = get(key);
        log.info("ShiroCache.remove(" + key + ") value " + v);
        springCache.evict(key);
        return v;
    }

    @Override
    public void clear() throws CacheException {
        log.info("ShiroCache.clear()");
        springCache.clear();
    }

    @Override
    public int size() {
        return keys().size();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Set<K> keys() {
        log.info("ShiroCache.keys()");

        return (Set<K>) springCacheManager.getCacheNames();
    }

    @Override
    public Collection<V> values() {
        log.info("ShiroCache.values()");
        List<V> list = new ArrayList<>();
        Set<K> keys = keys();
        for (K k : keys) {
            list.add(this.get(k));
        }
        return list;
    }
}
