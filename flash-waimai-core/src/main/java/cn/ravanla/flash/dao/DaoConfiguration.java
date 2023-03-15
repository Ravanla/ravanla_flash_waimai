package cn.ravanla.flash.dao;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Name: DabConfiguration<br>
 * User: ZYF
 * Date: 2020/2/27<br>
 * Time: 13:54<br>
 */
@Configuration
@EnableJpaRepositories("cn.ravanla.flash.dao")
public class DaoConfiguration {
}
