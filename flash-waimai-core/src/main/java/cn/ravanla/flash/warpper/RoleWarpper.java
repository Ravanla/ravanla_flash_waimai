package cn.ravanla.flash.warpper;

import cn.ravanla.flash.service.system.impl.ConstantFactory;

import java.util.List;
import java.util.Map;

/**
 * 角色列表的包装类
 *
 * @author fengshuonan
 * @date 2021年2月19日10:59:02
 */
public class RoleWarpper extends BaseControllerWarpper {

    public RoleWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
        map.put("pName", ConstantFactory.me().getSingleRoleName((Long) map.get("pid")));
        map.put("deptName", ConstantFactory.me().getDeptName((Long) map.get("deptid")));
    }

}
