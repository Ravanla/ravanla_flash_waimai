package cn.ravanla.flash.cache;

import cn.ravanla.flash.bean.core.ShiroUser;
import cn.ravanla.flash.cache.impl.EhcacheDao;
import cn.ravanla.flash.utils.HttpKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户登录时，生成的Token与用户ID的对应关系
 */
/**

 用于缓存token
 */
@Service
public class TokenCache {
    //自动装配EhcacheDao组件
    @Autowired
    private EhcacheDao ehcacheDao;

    /**

     将key-value存入缓存
     @param key 存入缓存的键
     @param value 存入缓存的值
     */
    public void put(String key,Object value){
        ehcacheDao.hset(EhcacheDao.SESSION,key,value);
    }
    /**

     从缓存中获取指定key的值
     @param key 获取的键
     @param klass 获取的值的类型
     @return 缓存中指定key对应的值
     */
    public <T> T get(String key,Class<T> klass){
        return ehcacheDao.hget(EhcacheDao.SESSION, key,klass);
    }
    /**

     将token和userId存入缓存
     @param token 存入缓存的token
     @param idUser 存入缓存的userId
     */
    public void put(String token, Long idUser) {
        ehcacheDao.hset(EhcacheDao.SESSION,token, idUser);
    }
    /**

     从缓存中获取指定token对应的userId
     @param token 获取userId的token
     @return 缓存中指定token对应的userId
     */
    public Long getToken(String token) {
        return ehcacheDao.hget(EhcacheDao.SESSION,token,Long.class);
    }
    /**

     从缓存中获取idUser
     @return 缓存中存储的idUser
     */
    public Long getIdUser(){
        return ehcacheDao.hget(EhcacheDao.SESSION, HttpKit.getToken(),Long.class );
    }
    /**

     从缓存中删除指定的token
     @param token 删除的token
     */
    public void remove(String token) {
        ehcacheDao.hdel(EhcacheDao.SESSION,token+"user");
    }
    /**

     将token和ShiroUser存入缓存
     @param token 存入缓存的token
     @param shiroUser 存入缓存的ShiroUser对象
     */
    public void setUser(String token, ShiroUser shiroUser){
        ehcacheDao.hset(EhcacheDao.SESSION,token+"user",shiroUser);
    }
    /**

     从缓存中获取指定token对应的ShiroUser对象
     @param token 获取ShiroUser对象的token
     @return 缓存中指定token对应的ShiroUser对象
     */
    public ShiroUser getUser(String token){
        return ehcacheDao.hget(EhcacheDao.SESSION,token+"user",ShiroUser.class);
    }
}