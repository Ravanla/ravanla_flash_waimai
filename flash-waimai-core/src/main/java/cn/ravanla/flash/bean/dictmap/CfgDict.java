package cn.ravanla.flash.bean.dictmap;

import cn.ravanla.flash.bean.dictmap.base.AbstractDictMap;

/**
 * 字典map
 *
 * @author fengshuonan
 * @date 2021-05-06 15:43
 */
public class CfgDict extends AbstractDictMap {

    @Override
    public void init() {
        put("id","参数id");
        put("cfgName","参数名称");
        put("cfgDesc","备注");
    }

    @Override
    protected void initBeWrapped() {

    }
}
