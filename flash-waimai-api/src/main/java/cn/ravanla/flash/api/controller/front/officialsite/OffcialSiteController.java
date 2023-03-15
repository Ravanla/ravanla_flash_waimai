package cn.ravanla.flash.api.controller.front.officialsite;

import cn.ravanla.flash.api.controller.BaseController;
import cn.ravanla.flash.bean.entity.cms.Article;
import cn.ravanla.flash.bean.enumeration.cms.ChannelEnum;
import cn.ravanla.flash.bean.vo.front.Rets;
import cn.ravanla.flash.bean.vo.offcialsite.BannerVo;
import cn.ravanla.flash.bean.vo.offcialsite.News;
import cn.ravanla.flash.bean.vo.offcialsite.Product;
import cn.ravanla.flash.bean.vo.offcialsite.Solution;
import cn.ravanla.flash.service.cms.ArticleService;
import cn.ravanla.flash.service.cms.BannerService;
import cn.ravanla.flash.utils.Maps;
import cn.ravanla.flash.utils.factory.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * TODO
 *
 * @author ravanla
 * @return
 * @date 3/15/2023
 * @throws
 **/
@RestController
@RequestMapping("/offcialsite")
public class OffcialSiteController extends BaseController {

    @Autowired
    private BannerService bannerService;
    @Autowired
    private ArticleService articleService;

    @RequestMapping(method = RequestMethod.GET)
    public Object index() {
//        Map<String, Object> dataMap = Maps.newHashMap();
//
//        BannerVo banner = bannerService.queryIndexBanner();
//        dataMap.put("banner", banner);
//        List<News> newsList = new ArrayList<>();
//        List<Article> articles = articleService.queryIndexNews();
//        for (Article article : articles) {
//            News news = new News();
//            news.setDesc(article.getTitle());
//            news.setUrl("/article?id=" + article.getId());
//            news.setSrc("static/images/icon/user.png");
//            newsList.add(news);
//        }
//        dataMap.put("newsList", newsList);
//
//        List<Product> products = new ArrayList<>();
//        Page<Article> articlePage = articleService.query(1, 4, ChannelEnum.PRODUCT.getId());
//        for (Article article : articlePage.getRecords()) {
//            Product product = new Product();
//            product.setId(article.getId());
//            product.setName(article.getTitle());
//            product.setImg(article.getImg());
//            products.add(product);
//        }
//        dataMap.put("productList", products);
//
//        List<Solution> solutions = new ArrayList<>();
//        articlePage = articleService.query(1, 4, ChannelEnum.SOLUTION.getId());
//        for (Article article : articlePage.getRecords()) {
//            Solution solution = new Solution();
//            solution.setId(article.getId());
//            solution.setName(article.getTitle());
//            solution.setImg(article.getImg());
//            solutions.add(solution);
//        }
//        dataMap.put("solutionList", solutions);
//        Map map = Maps.newHashMap("data",dataMap);
//        return Rets.success(map);


        // 创建一个Map，用于保存数据
        Map dataMap = Maps.newHashMap();

        // 从bannerService中获取首页banner信息
        BannerVo banner = bannerService.queryIndexBanner();
        // 把banner信息放入dataMap中
        dataMap.put("banner", banner);
        // 创建一个List用于保存新闻信息
        List<News> newsList = new ArrayList<>();
        // 从articleService中获取首页新闻
        List<Article> articles = articleService.queryIndexNews();
        // 遍历新闻，把新闻信息放入newsList
        for (Article article : articles) {
            News news = new News();
            news.setDesc(article.getTitle());
            news.setUrl("/article?id=" + article.getId());
            news.setSrc("static/images/icon/user.png");
            newsList.add(news);
        }
        // 把newsList放入dataMap
        dataMap.put("newsList", newsList);

        // 创建一个List用于保存产品信息
        List<Product> products = new ArrayList<>();
        // 从articleService中获取product频道的文章，每页4篇
        Page<Article> articlePage = articleService.query(1, 4, ChannelEnum.PRODUCT.getId());
        // 遍历文章，把文章信息放入products
        for (Article article : articlePage.getRecords()) {
            Product product = new Product();
            product.setId(article.getId());
            product.setName(article.getTitle());
            product.setImg(article.getImg());
            products.add(product);
        }
        // 把products放入dataMap
        dataMap.put("productList", products);

        // 创建一个List用于保存解决方案信息
        List<Solution> solutions = new ArrayList<>();

        // 从articleService中获取solution频道的文章，每页4篇
        articlePage = articleService.query(1, 4, ChannelEnum.SOLUTION.getId());

        // 遍历文章，把文章信息放入solutions
        for (Article article : articlePage.getRecords()) {
            Solution solution = new Solution();
            solution.setId(article.getId());
            solution.setName(article.getTitle());
            solution.setImg(article.getImg());
            solutions.add(solution);
        }

        // 把solutions放入dataMap
        dataMap.put("solutionList", solutions);

        // 创建一个Map，用于保存返回值
        Map map = Maps.newHashMap("data", dataMap);
        return Rets.success(map);
    }
}
