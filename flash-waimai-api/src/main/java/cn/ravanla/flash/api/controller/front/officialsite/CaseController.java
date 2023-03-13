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
import cn.ravanla.flash.utils.factory.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/offcialsite/case")
public class CaseController extends BaseController {
    @Autowired
    private BannerService bannerService;
    @Autowired
    private ArticleService articleService;

    @RequestMapping(method = RequestMethod.GET)
    // 方法会查询Banner和Article数据，组装成一个Map对象，最后封装成一个响应对象返回给前端。
    public Object index() {
        Map<String, Object> dataMap = new HashMap<>(2);

        // index方法先调用bannerService的queryBanner方法查询Banner数据，并将查询结果存入一个BannerVo对象中
        BannerVo banner = bannerService.queryBanner(BannerTypeEnum.CASE.getValue());

        // 将BannerVo对象存入一个名为dataMap的HashMap中
        dataMap.put("banner", banner);

        List<Product> products = new ArrayList<>();
        // 调用articleService的query方法查询Article数据，
        // 获取枚举类型ChannelEnum中PRODUCT成员的ID属性
        Page<Article> articlePage = articleService.query(1, 10, ChannelEnum.PRODUCT.getId());

        for (Article article : articlePage.getRecords()) {
            // 将查询结果转化为Product对象，并将Product对象存入一个名为products的ArrayList中
            products.add(new Product(article.getId(), article.getTitle(), article.getImg()));
        }
        // "caseList"是"dataMap"的一个键，而"products"是该键对应的值，即一个包含产品信息的列表。
        // 将 banner 和 caseList 两个键值对存储在 dataMap 中
        dataMap.put("caseList", products);


        Map map = new HashMap(1);
        // 重新封装成json格式返回给前端，键值对
        // map 中的 data 键对应的值就是之前创建的 dataMap 对象。
        map.put("data", dataMap);
        return Rets.success(map);

    }
}
