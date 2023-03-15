package cn.ravanla.flash.api;

import cn.ravanla.flash.dao.BaseRepositoryFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * ApiApplication
 *
 * @Author ravanla
 * @version 2020/9/11 0011
 */
// 启用缓存
@EnableCaching

// 标记为SpringBoot应用，以便扫描@SpringBootConfiguration等注解
@SpringBootApplication

// 扫描所有cn.ravanla.flash包及其子包下的组件
@ComponentScan(basePackages = "cn.ravanla.flash")

// 扫描所有cn.ravanla.flash.bean.entity包及其子包下的实体类
@EntityScan(basePackages="cn.ravanla.flash.bean.entity")

// 扫描所有cn.ravanla.flash.dao包及其子包下的自定义Repository
@EnableJpaRepositories(basePackages = "cn.ravanla.flash.dao", repositoryFactoryBeanClass = BaseRepositoryFactoryBean.class)

// 启用JPA审计
@EnableJpaAuditing

public class ApiApplication extends SpringBootServletInitializer {

    // 注册事务管理器，使用默认的DataSource
    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    // 配置应用启动入口
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ApiApplication.class);
    }

    // 主函数，用于本地启动应用
    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class);
    }
}
