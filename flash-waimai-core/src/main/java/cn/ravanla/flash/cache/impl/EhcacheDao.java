package cn.ravanla.flash.cache.impl;

import cn.ravanla.flash.cache.CacheDao;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;

/**
 * EhcacheDao
 *
 *@Author ravanla
 * @version 2020/9/12 0012
 */
@Component
public class EhcacheDao implements CacheDao {
    // 缓存常量，永不过期
    public static final String CONSTANT = "CONSTANT";
    public static final String SESSION = "SESSION";
    @Resource
    private CacheManager cacheManager;

    // 在指定的缓存键(key)中设置哈希表(key)字段为指定值(value)
    @Override
    public void hset(Serializable key, Serializable k, Object val) {
        Cache cache = cacheManager.getCache(String.valueOf(key));
        cache.put(k,val);
    }

    // 获取指定缓存键(key)哈希表(key)中的值
    @Override
    public Serializable hget(Serializable key, Serializable k) {
        Cache cache = cacheManager.getCache(String.valueOf(key));
        return cache.get(k,String.class);
    }

    // 获取指定缓存键(key)哈希表(key)中的值，并按指定类型解析返回
    @Override
    public <T>T hget(Serializable key, Serializable k,Class<T> klass) {
        Cache cache = cacheManager.getCache(String.valueOf(key));
        return cache.get(k,klass);
    }

    // 设置指定键(key)的值为指定值(value)，缓存时间为默认时间
    @Override
    public void set(Serializable key, Object val) {
        Cache cache = cacheManager.getCache(CONSTANT);
        cache.put(key,val);
    }

    // 获取指定键(key)的值，并按指定类型解析返回
    @Override
    public <T>T get(Serializable key,Class<T> klass) {
        return cacheManager.getCache(CONSTANT).get(String.valueOf(key),klass);
    }

    // 获取指定键(key)的值
    @Override
    public String get(Serializable key) {
        return cacheManager.getCache(CONSTANT).get(String.valueOf(key),String.class);
    }

    // 删除指定键(key)的缓存
    @Override
    public void del(Serializable key) {
        cacheManager.getCache(CONSTANT).put(String.valueOf(key),null);
    }

    // 删除指定缓存键(key)的哈希表(key)字段
    @Override
    public void hdel(Serializable key, Serializable k) {
        cacheManager.getCache(String.valueOf(key)).put(String.valueOf(k),null);
    }

    // 删除指定缓存键(key)的哈希表(key)字段和删除指定键(key)的缓存是不同的操作。
    // 当我们删除指定缓存键(key)的哈希表(key)字段时，实际上是删除了哈希表(key)中的一个字段，
    // 而哈希表(key)中可能还有其他字段，而不是完全删除了该键。

    // 在代码中，EhcacheDao 类中的 hdel 方法实现了删除指定缓存键(key)的哈希表(key)字段的操作。
    // 当我们删除指定键(key)的缓存时，实际上是删除了该键所对应的整个缓存内容，
    // 包括哈希表、列表等等。在代码中，EhcacheDao 类中的 del 方法实现了删除指定键(key)的缓存的操作。
    // 因此，这两种操作的区别在于删除的粒度不同，一个是删除哈希表中的一个字段，一个是删除整个缓存内容。
}
