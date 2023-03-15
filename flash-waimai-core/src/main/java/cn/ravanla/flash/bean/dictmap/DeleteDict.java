package cn.ravanla.flash.bean.dictmap;

import cn.ravanla.flash.bean.dictmap.base.AbstractDictMap;

/**
 * 用于删除业务的字典
 *
 * @author fengshuonan
 * @date 2021-05-06 15:01
 */
public class DeleteDict extends AbstractDictMap {

    // 实现父类中的init()方法，初始化成员变量
    @Override public void init() {
        // 将角色名称放入roleId对应的键值中
        put("roleId","角色名称");
        // 将部门名称放入deptId对应的键值中
        put("deptId", "部门名称");
        // 将菜单名称放入menuId对应的键值中
        put("menuId", "菜单名称");
        // 将字典名称放入dictId对应的键值中
        put("dictId", "字典名称");
        // 将标题放入noticeId对应的键值中
        put("noticeId", "标题");
    }

    // 实现父类中的initBeWrapped()方法，对成员变量进行包装
    @Override protected void initBeWrapped() {

        // put 字段包装器方法名称

        // 将roleId对应的键值包装成getCacheObject方法
        putFieldWrapperMethodName("roleId","getCacheObject");
        // 将deptId对应的键值包装成getCacheObject方法
        putFieldWrapperMethodName("deptId","getCacheObject");
        // 将menuId对应的键值包装成getCacheObject方法
        putFieldWrapperMethodName("menuId","getCacheObject");
        // 将dictId对应的键值包装成getCacheObject方法
        putFieldWrapperMethodName("dictId","getCacheObject");
        // 将noticeId对应的键值包装成getCacheObject方法
        putFieldWrapperMethodName("noticeId","getCacheObject");
    }
}
