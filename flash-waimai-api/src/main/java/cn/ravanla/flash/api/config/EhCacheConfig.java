package cn.ravanla.flash.api.config;

import net.sf.ehcache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 * 加载ehcache缓存配置<br>
 * @version 2020-07-24
 *
 *@Author ravanla
 *
 */
//声明这是一个配置类，并启用缓存功能
@Configuration
@EnableCaching
public class EhCacheConfig {


    // 声明一个 Bean 方法，返回一个 EhCacheCacheManager 类型的对象，用于管理 EhCache 的缓存
    @Bean
    public EhCacheCacheManager cacheManager(CacheManager cacheManager) {
        return new EhCacheCacheManager(cacheManager);
    }


    // 声明一个 Bean 方法，返回一个 EhCacheManagerFactoryBean 类型的对象，用于创建和配置 EhCache 的 CacheManager
    @Bean
    public EhCacheManagerFactoryBean ehcache() {
        // 创建一个 EhCacheManagerFactoryBean 对象
        EhCacheManagerFactoryBean ehCacheManagerFactoryBean = new EhCacheManagerFactoryBean();
        // 设置该对象的配置文件位置为类路径下的 ehcache.xml 文件
        ehCacheManagerFactoryBean.setConfigLocation(new ClassPathResource("ehcache.xml"));
        //  返回该对象
        return ehCacheManagerFactoryBean;
    }
}
