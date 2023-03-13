
package cn.ravanla.flash.dao.system;


import cn.ravanla.flash.bean.entity.system.Dict;
import cn.ravanla.flash.dao.BaseRepository;

import java.util.List;

public interface DictRepository  extends BaseRepository<Dict,Long> {
    List<Dict> findByPid(Long pid);
    List<Dict> findByNameAndPid(String name,Long pid);

    List<Dict> findByNameLike(String name);
}
