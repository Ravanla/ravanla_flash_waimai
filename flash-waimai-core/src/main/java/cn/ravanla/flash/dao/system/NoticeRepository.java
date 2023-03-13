package cn.ravanla.flash.dao.system;


import cn.ravanla.flash.bean.entity.system.Notice;
import cn.ravanla.flash.dao.BaseRepository;

import java.util.List;

/**
 * Created  on 2020/3/21 0021.
 *
 *@Author ravanla
 */
public interface NoticeRepository extends BaseRepository<Notice,Long> {
    List<Notice> findByTitleLike(String name);
}
