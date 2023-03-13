package cn.ravanla.flash.bean.vo.offcialsite;

import cn.ravanla.flash.bean.entity.cms.Banner;
import lombok.Data;

import java.util.List;

@Data
public class BannerVo {
    private Integer index = 0;
    private List<Banner> list;

}
