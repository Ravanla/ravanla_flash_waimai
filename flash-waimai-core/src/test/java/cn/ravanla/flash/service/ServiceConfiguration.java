package cn.ravanla.flash.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Name: ServiceConfiguration<br>
 * User: Yao<br>
 * Date: 2020/2/27<br>
 * Time: 13:54<br>
 */
@SpringBootApplication
@EntityScan(basePackages="cn.ravanla.flash.bean.entity")
@EnableJpaRepositories(basePackages= "cn.ravanla.flash.dao")
public class ServiceConfiguration {



    public static void main(String[] args) {
        SpringApplication.run(ServiceConfiguration.class);
    }
}
