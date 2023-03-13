package cn.ravanla.flash.api.controller.cms;

import cn.ravanla.flash.api.controller.BaseController;
import cn.ravanla.flash.bean.constant.factory.PageFactory;
import cn.ravanla.flash.bean.core.BussinessLog;
import cn.ravanla.flash.bean.dictmap.CommonDict;
import cn.ravanla.flash.bean.entity.cms.Article;
import cn.ravanla.flash.bean.enumeration.Permission;
import cn.ravanla.flash.bean.vo.front.Rets;
import cn.ravanla.flash.bean.vo.query.SearchFilter;
import cn.ravanla.flash.service.cms.ArticleService;
import cn.ravanla.flash.utils.DateUtil;
import cn.ravanla.flash.utils.factory.Page;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 文章管理
 */
// 在这段代码中，我们定义了一个名为ArticleMgrController的RESTful API控制器，用于管理文章信息。它定义了以下四个请求处理方法：


// 这些请求处理方法分别对应不同的HTTP请求类型（POST、DELETE、GET、GET），并且都使用@RequestMapping注解来指定请求的URL路径。
// 同时，这些方法还使用了@BussinessLog、@RequiresPermissions等注解来增加业务日志记录和权限控制等功能，提高了系统的可用性和安全性。
@RestController
@RequestMapping("/article")
public class ArticleMgrController extends BaseController {

    @Autowired
    private ArticleService articleService;
    @RequestMapping(method = RequestMethod.POST)
    @BussinessLog(value = "编辑文章",key="name",dict = CommonDict.class)
    @RequiresPermissions(value = {Permission.ARTICLE_EDIT})
    // save()方法：用于保存或更新文章信息。根据传入的JSON数据，判断是新增还是修改文章信息，并将其持久化到数据库中。该方法需要进行身份验证和权限验证，只有具有文章编辑权限的用户才能够进行操作。

    public Object save(){
        Article article = getFromJson(Article.class);
        if(article.getId() != null){
            Article old = articleService.get(article.getId());
            old.setAuthor(article.getAuthor());
            old.setContent(article.getContent());
            old.setIdChannel(article.getIdChannel());
            old.setImg(article.getImg());
            old.setTitle(article.getTitle());
           articleService.update(old);
        }else {
            articleService.insert(article);
        }
        return Rets.success();
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @BussinessLog(value = "删除文章",key="id",dict = CommonDict.class)
    @RequiresPermissions(value = {Permission.ARTICLE_DEL})
    // remove()方法：用于删除指定ID的文章信息。该方法需要进行身份验证和权限验证，只有具有文章删除权限的用户才能够进行操作。

    public Object remove(Long id){
        articleService.delete(id);
        return Rets.success();
    }

    @RequestMapping(method = RequestMethod.GET)
    @RequiresPermissions(value = {Permission.ARTICLE})
    // get()方法：用于获取指定ID的文章信息。该方法需要进行身份验证和权限验证，只有具有文章查看权限的用户才能够进行操作。

    public Object get(@Param("id") Long id) {
        Article article = articleService.get(id);
        return Rets.success(article);
    }

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @RequiresPermissions(value = {Permission.ARTICLE})
    // list()方法：用于获取文章列表信息。
    // 可以根据文章标题、作者、发布时间等条件进行筛选和排序，并支持分页查询。
    // 该方法需要进行身份验证和权限验证，只有具有文章查看权限的用户才能够进行操作。

    // 这段代码定义了一个名为list的方法，使用了@RequestParam注解获取前端传来的参数，
    // 包括title、author、startDate和endDate，它们都是可选参数，即在请求时可以不传递。
    public Object list(@RequestParam(required = false) String title,
                       @RequestParam(required = false) String author,
                       @RequestParam(required = false) String startDate,
                       @RequestParam(required = false) String endDate
                       ) {
        // 该方法创建了一个PageFactory对象，并调用defaultPage方法创建一个默认的Page对象。
        Page<Article> page = new PageFactory<Article>().defaultPage();
        // 接着，该方法通过调用Page对象的addFilter方法，将title、author、startDate和endDate这些参数作为过滤条件添加到Page对象中。
        page.addFilter("title", SearchFilter.Operator.LIKE,title);
        page.addFilter("author", SearchFilter.Operator.EQ,author);
        page.addFilter("createTime", SearchFilter.Operator.GTE, DateUtil.parse(startDate,"yyyyMMddHHmmss"));
        page.addFilter("createTime", SearchFilter.Operator.LTE, DateUtil.parse(endDate,"yyyyMMddHHmmss"));
        // 最后，该方法调用articleService对象的queryPage方法，将包含过滤条件的Page对象作为参数传递进去，从而获取查询结果。
        page = articleService.queryPage(page);
        // 最终，该方法将查询结果通过Rets.success方法返回给前端。
        return Rets.success(page);
        //具体格式取决于代码实现中使用的序列化方式。如果使用了Spring Boot自带的Jackson序列化工具，那么返回的就是JSON格式的字符串。
    }
}
