package cn.ravanla.flash.api.config;

import cn.ravanla.flash.utils.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * swagger在线文档配置<br>
 * 项目启动后可通过地址：http://host:ip/swagger-ui.html 查看在线文档
 * @version 2020-07-24
 *
 *@Author ravanla
 */

//声明这是一个配置类，并启用 Swagger2 功能
@Configuration
@EnableSwagger2
public class Swagger2Configuration {
    // 声明一个 Bean 方法，返回一个 Docket 类型的对象，用于创建和配置 REST API 文档
    @Bean
    public Docket createRestApi() {
// 添加head参数start
// 创建一个 ParameterBuilder 对象，用于构建参数信息
        ParameterBuilder tokenPar = new ParameterBuilder();
// 创建一个 List 对象，用于存储参数信息
        List<Parameter> pars = new ArrayList<Parameter>();
// 设置参数的名称、描述、类型、位置等属性，并将其添加到 pars 中
        tokenPar.name(Constants.TOKEN_NAME).description("Token").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        pars.add(tokenPar.build());
// 添加head参数end

// 返回一个 Docket 对象，并设置其文档类型、信息、扫描范围、路径规则等属性，并将 pars 作为全局参数
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.ravanla.flash.api.controller"))
                .paths(PathSelectors.any())
                .build().globalOperationParameters(pars);
    }

    // 声明一个私有方法，返回一个 ApiInfo 类型的对象，用于创建和配置 API 的基本信息
    private ApiInfo apiInfo() {
// 返回一个 ApiInfoBuilder 对象，并设置其标题、描述、服务条款、联系方式、版本等属性
        return new ApiInfoBuilder()
                .title("flash-waimai api")
                .description("快速构建外卖系统")
                .termsOfServiceUrl("microapp.store")
                .contact("www.microapp.store")
                .version("1.0")
                .build();
    }
}
