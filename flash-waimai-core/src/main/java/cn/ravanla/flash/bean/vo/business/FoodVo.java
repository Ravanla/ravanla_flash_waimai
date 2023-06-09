package cn.ravanla.flash.bean.vo.business;

import cn.ravanla.flash.bean.entity.front.Food;
import lombok.Data;

import java.util.List;

/**
 * 接收食品信息参数
 * @Author ravanla
 * @date ：Created in 2021/9/7 10:36
 */
@Data
public class FoodVo  extends Food {
    private Long id;
    private String name;
    private String descript;
    private Long idMenu;
    private String imagePath;
    private Long idShop;
    private List<SpecVo> specs;
    private String specsJson;
    private String attributesJson;
    private Long category_id;


}
