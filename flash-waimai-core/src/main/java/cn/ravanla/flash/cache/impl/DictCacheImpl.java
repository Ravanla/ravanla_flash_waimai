package cn.ravanla.flash.cache.impl;

import cn.ravanla.flash.bean.constant.cache.CacheKey;
import cn.ravanla.flash.bean.entity.system.Dict;
import cn.ravanla.flash.cache.CacheDao;
import cn.ravanla.flash.cache.DictCache;
import cn.ravanla.flash.dao.system.DictRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * DictCacheImpl
 *
 *@Author ravanla
 * @version 2020/12/23 0023
 */
@Component
// DictCacheImpl则是一个实现了DictCache接口的实现类，
// 它主要实现了 缓存字典数据 和 根据缓存键获取缓存值 等操作。
public class DictCacheImpl implements DictCache {
    @Autowired
    //
    private DictRepository dictRepository;

    @Autowired
    // CacheDao是一个接口，实现了缓存的一些基本操作
    private CacheDao cacheDao;

    // 根据父级字典名称查询子级字典列表
    @Override
    public List<Dict> getDictsByPname(String dictName) {
        // CacheKey是一个枚举类，定义了缓存键的一些常量，包括DICT、DICT_NAME等。
        return (List<Dict>) cacheDao.hget(EhcacheDao.CONSTANT, CacheKey.DICT+dictName,List.class);
    }

    // 根据字典ID获取字典名称
    @Override
    public String getDict(Long dictId) {
        return (String) get(CacheKey.DICT_NAME+String.valueOf(dictId));
    }

    // 缓存字典数据到缓存中
    @Override
    public void cache() {
        List<Dict> list = dictRepository.findByPid(0L); // 查询所有一级字典列表
        for(Dict dict:list){
            List<Dict> children  = dictRepository.findByPid(dict.getId()); // 查询当前字典的子级字典列表
            if(children.isEmpty()) { // 如果子级字典为空则跳过
                continue;
            }
            set(String.valueOf(dict.getId()), children); // 设置缓存键和值
            set(dict.getName(), children); // 设置缓存键和值
            for(Dict child:children){
                set(CacheKey.DICT_NAME+String.valueOf(child.getId()),child.getName()); // 设置缓存键和值
            }
        }
    }

    // 根据键(key)获取缓存中的值
    @Override
    public Object get(String key) {
        return cacheDao.hget(EhcacheDao.CONSTANT,CacheKey.DICT+key);
    }

    // 设置缓存键(key)和对应的值(val)
    @Override
    public void set(String key, Object val) {
        cacheDao.hset(EhcacheDao.CONSTANT,CacheKey.DICT+key,val);
    }
}
