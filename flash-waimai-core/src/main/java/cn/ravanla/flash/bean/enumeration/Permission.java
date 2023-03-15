package cn.ravanla.flash.bean.enumeration;

/**
 * 权限编码列表<br>
 * 权限编码需要和菜单中的菜单编码一致
 * @Author ravanla
 * @date ：Created in 2021/7/31 11:05
 */
public interface Permission {

    //系统管理
    String CFG = "cfg";
    String CFG_EDIT = "cfg.edit";
    String CFG_DEL = "cfg.delete";
    String DICT = "dict";
    String DICT_EDIT = "dict.edit";
    String LOG = "log";
    String LOG_CLEAR = "log.clear";
    String LOGIN_LOG = "login.log";
    String LOGIN_LOG_CLEAR = "login.log.clear";
    String ROLE = "role";
    String ROLE_EDIT = "role.edit";
    String ROLE_DEL = "role.delete";
    String TASK = "task";
    String TASK_EDIT = "task.edit";
    String TASK_DEL = "task.delete";
    String MENU = "menu";
    String MENU_EDIT = "menu.edit";
    String MENU_DEL = "menu.delete";
    String USER = "mgr";
    String USER_EDIT = "mgr.edit";
    String USER_DEL = "mgr.delete";
    String DEPT = "dept";
    String DEPT_EDIT = "dept.edit";
    String DEPT_DEL = "dept.delete";

    //消息管理
    String MSG = "msg";
    String MSG_CLEAR = "msg.clear";
    String MSG_SENDER = "msg.sender";
    String MSG_SENDER_EDIT = "msg.sender.edit";
    String MSG_SENDER_DEL = "msg.sender.delete";
    String MSG_TPL = "msg.tpl";
    String MSG_TPL_EDIT = "msg.tpl.edit";
    String MSG_TPL_DEL = "msg.tpl.delete";

    //CMS管理
    String ARTICLE = "article";
    String ARTICLE_EDIT = "article.edit";
    String ARTICLE_DEL = "article.delete";
    String BANNER = "banner";
    String BANNER_EDIT = "banner.edit";
    String BANNER_DEL = "banner.delete";
    String CHANNEL = "channel";
    String CHANNEL_EDIT = "channel.edit";
    String CHANNEL_DEL = "channel.delete";
    String CONTACTS = "contacts";
    String FILE = "file";
    String FILE_UPLOAD = "file.upload";

/*
* CFG_EDIT：编辑系统参数的权限码
* CFG_DEL：删除系统参数的权限码
* DICT：数据字典管理
* DICT_EDIT：编辑数据字典的权限码
* LOG：系统日志管理
* LOG_CLEAR：清空系统日志的权限码
* LOGIN_LOG：登录日志管理
* LOGIN_LOG_CLEAR：清空登录日志的权限码
* ROLE：角色管理
* ROLE_EDIT：编辑角色的权限码
* ROLE_DEL：删除角色的权限码
* TASK：定时任务管理
* TASK_EDIT：编辑定时任务的权限码
* TASK_DEL：删除定时任务的权限码
* MENU：菜单管理
* MENU_EDIT：编辑菜单的权限码
* MENU_DEL：删除菜单的权限码
* USER：用户管理
* USER_EDIT：编辑用户的权限码
* USER_DEL：删除用户的权限码
* DEPT：部门管理
* DEPT_EDIT：编辑部门的权限码
* DEPT_DEL：删除部门的权限码
* MSG：消息管理
* MSG_CLEAR：清空消息的权限码
* MSG_SENDER：消息发送人管理
* MSG_SENDER_EDIT：编辑消息发送人的权限码
* MSG_SENDER_DEL：删除消息发送人的权限码
* MSG_TPL：消息模板管理
* MSG_TPL_EDIT：编辑消息模板的权限码
* MSG_TPL_DEL：删除消息模板的权限码
* ARTICLE：文章管理
* ARTICLE_EDIT：编辑文章的权限码
* ARTICLE_DEL：删除文章的权限码
* BANNER：轮播图管理
* BANNER_EDIT：编辑轮播图的权限码
* BANNER_DEL：删除轮播图的权限码
* CHANNEL：栏目管理
* CHANNEL_EDIT：编辑栏目的权限码
* CHANNEL_DEL：删除栏目的权限码
* CONTACTS：通讯录管理
* FILE：文件管理
* FILE_UPLOAD：上传文件的权限码
*
* */
}
