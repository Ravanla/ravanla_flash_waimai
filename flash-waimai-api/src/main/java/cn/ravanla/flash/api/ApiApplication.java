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
 *@Author ravanla
 * @version 2020/9/11 0011
 */
@EnableCaching
@SpringBootApplication
@ComponentScan(basePackages = "cn.ravanla.flash")
@EntityScan(basePackages="cn.ravanla.flash.bean.entity")
@EnableJpaRepositories(basePackages = "cn.ravanla.flash.dao", repositoryFactoryBeanClass = BaseRepositoryFactoryBean.class)
@EnableJpaAuditing
public class ApiApplication extends SpringBootServletInitializer {
    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ApiApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class);
    }
}
