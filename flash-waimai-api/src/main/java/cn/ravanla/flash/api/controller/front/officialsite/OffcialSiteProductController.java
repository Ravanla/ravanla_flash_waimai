package cn.ravanla.flash.api.controller.front.officialsite;

import cn.ravanla.flash.api.controller.BaseController;
import cn.ravanla.flash.bean.entity.cms.Article;
import cn.ravanla.flash.bean.enumeration.cms.BannerTypeEnum;
import cn.ravanla.flash.bean.enumeration.cms.ChannelEnum;
import cn.ravanla.flash.bean.vo.front.Rets;
import cn.ravanla.flash.bean.vo.offcialsite.BannerVo;
import cn.ravanla.flash.bean.vo.offcialsite.Product;
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

@RestController
@RequestMapping("/offcialsite/product")
public class OffcialSiteProductController extends BaseController {
    @Autowired
    private BannerService bannerService;
    @Autowired
    private ArticleService articleService;

    @RequestMapping(method = RequestMethod.GET)
    public Object index() {
        Map<String, Object> dataMap = Maps.newHashMap();

                BannerVo banner = bannerService.queryBanner(BannerTypeEnum.SOLUTION.getValue());
        dataMap.put("banner", banner);

        List<Product> products = new ArrayList<>();
        Page<Article> articlePage = articleService.query(1, 10, ChannelEnum.PRODUCT.getId());
        for (Article article : articlePage.getRecords()) {
            products.add(new Product(article.getId(), article.getTitle(), article.getImg()));
        }
        dataMap.put("productList", products);

        Map map =  Maps.newHashMap("data",dataMap);
        return Rets.success(map);

    }
}
