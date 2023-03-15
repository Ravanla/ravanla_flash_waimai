package cn.ravanla.flash.cache.impl;

import cn.ravanla.flash.bean.entity.system.Cfg;
import cn.ravanla.flash.cache.CacheDao;
import cn.ravanla.flash.cache.ConfigCache;
import cn.ravanla.flash.dao.system.CfgRepository;
import cn.ravanla.flash.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 全局参数缓存实现类
 *
 *@Author ravanla
 * @version 2020/12/20 0020
 */
@Service
public class ConfigCacheImpl implements ConfigCache {
    // 创建日志记录器
    private static final Logger logger = LoggerFactory.getLogger(ConfigCacheImpl.class);
    // 注入CfgRepository和CacheDao
    @Autowired
    private CfgRepository cfgRepository;
    @Autowired
    private CacheDao cacheDao;

    // 获取指定键的缓存值
    @Override
    public Object get(String key) {
        return (String) cacheDao.hget(EhcacheDao.CONSTANT,key);
    }

    // 获取指定键的缓存值，如果local为true，则从缓存中获取，否则从数据库中获取
    @Override
    public String get(String key, boolean local) {
        String ret = null;
        if(local) {
            ret = (String) get(key);
        }else{
            ret = cfgRepository.findByCfgName(key).getCfgValue();
            set(key,ret);
        }
        return ret;
    }

    // 获取指定键的缓存值，如果值为空则返回默认值
    @Override
    public String get(String key, String def) {
        String ret = (String) get(key);
        if(StringUtils.isEmpty(ret)){
            return ret;
        }
        return ret;
    }

    // 设置指定键的缓存值
    @Override
    public void set(String key, Object val) {
        cacheDao.hset(EhcacheDao.CONSTANT,key,val);
    }

    // 删除指定键的指定值
    @Override
    public void del(String key, String val) {
        cacheDao.hdel(EhcacheDao.CONSTANT,val);
    }

    // 将所有的配置缓存到缓存服务器中
    @Override
    public void cache() {
        logger.info("reset config cache"); // 记录日志
        List<Cfg> list = cfgRepository.findAll(); // 获取所有配置
        if (list != null && !list.isEmpty()) {
            for (Cfg cfg : list) { // 遍历所有配置
                if (StringUtils.isNotEmpty(cfg.getCfgName()) && StringUtils.isNotEmpty(cfg.getCfgValue())) { //如果配置名和配置值都不为空
                    set(cfg.getCfgName(),cfg.getCfgValue()); // 将该配置缓存到缓存服务器中
                }
            }
        }
    }
}
