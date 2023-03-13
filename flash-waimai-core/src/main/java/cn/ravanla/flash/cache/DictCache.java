package cn.ravanla.flash.cache;

import cn.ravanla.flash.bean.entity.system.Dict;

import java.util.List;

/**
 * 字典缓存
 *
 *@Author ravanla
 * @version 2020/12/23 0023
 */
public interface DictCache  extends Cache{

    List<Dict> getDictsByPname(String dictName);
    String getDict(Long dictId);
}
