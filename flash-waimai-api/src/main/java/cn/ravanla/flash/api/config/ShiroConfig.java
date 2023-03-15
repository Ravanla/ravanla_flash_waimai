package cn.ravanla.flash.api.config;

import cn.ravanla.flash.api.interceptor.JwtFilter;
import cn.ravanla.flash.security.ApiRealm;
import cn.ravanla.flash.utils.Maps;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.Map;

/**
 * @Author ravanla
 * @date ：Created in 2021/7/30 23:08
 */
//声明这是一个配置类
@Configuration
public class ShiroConfig {
    //声明一个 Bean 方法，返回一个 DefaultWebSecurityManager 类型的对象，用于管理 Shiro 的安全管理器
    @Bean("securityManager")
    public DefaultWebSecurityManager getManager(ApiRealm realm) {
//创建一个 DefaultWebSecurityManager 对象
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
// 使用自己的realm，即 ApiRealm 类型的对象，用于认证和授权
        manager.setRealm(realm);

        /*
         * 关闭shiro自带的session，详情见文档
         * http://shiro.apache.org/session-management.html#SessionManagement-StatelessApplications%28Sessionless%29
         */
//创建一个 DefaultSubjectDAO 对象，用于管理 Subject 的持久化和缓存
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
//创建一个 DefaultSessionStorageEvaluator 对象，用于评估是否使用 Session 存储
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
//设置该对象不使用 Session 存储
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
//设置 subjectDAO 的 Session 存储评估器为 defaultSessionStorageEvaluator
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
//设置 manager 的 Subject DAO 为 subjectDAO
        manager.setSubjectDAO(subjectDAO);

        return manager;
    }

    //声明一个 Bean 方法，返回一个 ShiroFilterFactoryBean 类型的对象，用于创建和配置 Shiro 的过滤器
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean factory(DefaultWebSecurityManager securityManager) {
//创建一个 ShiroFilterFactoryBean 对象
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();

// 添加自己的过滤器并且取名为jwt，即 JwtFilter 类型的对象，用于实现 JWT 的认证和授权
        Map<String, Filter> filterMap =  Maps.newHashMap();
        filterMap.put("jwt", new JwtFilter());
        factoryBean.setFilters(filterMap);

//设置 factoryBean 的安全管理器为 securityManager
        factoryBean.setSecurityManager(securityManager);
//设置 factoryBean 的未授权页面为 /401
        factoryBean.setUnauthorizedUrl("/401");

        /*
         * 自定义url拦截规则
         * http://shiro.apache.org/web.html#urls-
         */
//创建一个 Map 对象，用于存储 url 和拦截规则的映射关系
        Map<String, String> filterRuleMap =  Maps.newHashMap();

// 访问401和404页面不通过我们的Filter
        filterRuleMap.put("/401", "anon");
        //swagger资源不拦截
        filterRuleMap.put("/swagger-ui/**", "anon");
        filterRuleMap.put("/v2/**","anon");
        filterRuleMap.put("/doc.html","anon");
        filterRuleMap.put("/webjars/**", "anon");
        filterRuleMap.put("/swagger-resources/**", "anon");
        filterRuleMap.put("/images/**", "anon");
        filterRuleMap.put("/configuration/security", "anon");
        filterRuleMap.put("/configuration/ui", "anon");
        //登录接口不拦截
        filterRuleMap.put("/account/login", "anon");

        // 所有请求通过我们自己的JWT Filter
        filterRuleMap.put("/**", "jwt");
        factoryBean.setFilterChainDefinitionMap(filterRuleMap);
        return factoryBean;
    }

    /**
     * 下面的代码是添加注解支持
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        // 强制使用cglib，防止重复代理和可能引起代理出错的问题
        // https://zhuanlan.zhihu.com/p/29161098
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}
