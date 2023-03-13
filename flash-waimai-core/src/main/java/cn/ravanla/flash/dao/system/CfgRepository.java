
package cn.ravanla.flash.dao.system;

import cn.ravanla.flash.bean.entity.system.Cfg;
import cn.ravanla.flash.dao.BaseRepository;

/**
 * 全局参数dao
 *
 * @Author ravanla
 * @date ：Created in 2021/6/29 12:50
 */
public interface CfgRepository extends BaseRepository<Cfg,Long> {

    Cfg findByCfgName(String cfgName);
}
