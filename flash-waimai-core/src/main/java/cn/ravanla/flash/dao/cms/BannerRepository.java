
package cn.ravanla.flash.dao.cms;

import cn.ravanla.flash.bean.entity.cms.Banner;
import cn.ravanla.flash.dao.BaseRepository;

import java.util.List;

public interface BannerRepository extends BaseRepository<Banner,Long> {
    /**
     * 查询指定类别的banner列表
     * @param type
     * @return
     */
    List<Banner> findAllByType(String type);
}
