package cn.ravanla.flash.api.controller.front.officialsite;

import cn.ravanla.flash.api.controller.BaseController;
import cn.ravanla.flash.bean.entity.cms.Article;
import cn.ravanla.flash.bean.vo.front.Rets;
import cn.ravanla.flash.service.cms.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/offcialsite/article")

public class ArticleController extends BaseController {
    // 在类中，使用了LoggerFactory和Logger来记录日志。
    private Logger logger = LoggerFactory.getLogger(ArticleController.class);


// 在这段代码中，使用@Autowired注解将ArticleService自动注入到ArticleController中。
// 在执行get方法时，可以直接调用ArticleService中的get方法，
// 而不需要在ArticleController中手动实例化一个ArticleService对象。
//ArticleService articleService = new ArticleService()
//在实际开发中，应该尽量遵循Spring的IOC容器管理

// @Autowired注解会根据类型进行自动装配，即根据ArticleService的类型寻找一个匹配的bean，
//Bean就是Spring框架中被容器管理的对象，包括应用程序中的核心对象，通过Spring的IoC容器实现依赖注入。
// 如果有多个匹配的bean，则会抛出异常。如果需要指定名称，则可以使用@Qualifier注解。
    @Autowired
    // @Autowired注解用于自动装配ArticleService，调用ArticleService的get方法获取文章信息，并以JSON格式返回给客户端。

    private ArticleService articleService;
    @RequestMapping(method = RequestMethod.GET)
    // 在get方法中，使用了@Param注解来接收客户端传递的请求参数，分别为id和type。
    public Object get(@Param("id") Long id,@Param("type")String type) {

        // logger.info("type:{},id:{}",type,id)用于记录请求的参数信息。
        logger.info("type:{},id:{}",type,id);

        // 最后，将从ArticleService中获取的文章信息以JSON格式返回给客户端。
         Article article = articleService.get(id);
        return Rets.success(article);
    }
}
