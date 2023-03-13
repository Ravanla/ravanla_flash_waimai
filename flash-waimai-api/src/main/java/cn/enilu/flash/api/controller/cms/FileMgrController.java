package cn.enilu.flash.api.controller.cms;

import cn.enilu.flash.api.controller.BaseController;
import cn.enilu.flash.bean.constant.factory.PageFactory;
import cn.enilu.flash.bean.entity.system.FileInfo;
import cn.enilu.flash.bean.enumeration.Permission;
import cn.enilu.flash.bean.vo.front.Rets;
import cn.enilu.flash.bean.vo.query.SearchFilter;
import cn.enilu.flash.service.system.FileService;
import cn.enilu.flash.utils.StringUtils;
import cn.enilu.flash.utils.factory.Page;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/*
* 文件管理模块的控制器类
* */
@RestController
@RequestMapping("/fileMgr")
public class FileMgrController extends BaseController {

    @Autowired
    private FileService fileService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    // 需要相应的权限才能访问
    @RequiresPermissions(value = {Permission.FILE})
    // 它包括了一个“list”方法，接受一个“originalFileName”的可选参数。
    public Object list(@RequestParam(required = false) String originalFileName
    ) {
        // 在方法内部，它首先创建了一个默认的分页对象“page”，
        Page<FileInfo> page = new PageFactory<FileInfo>().defaultPage();
        // 然后通过判断“originalFileName”参数是否为空来向“page”添加一个查询条件。
        if (StringUtils.isNotEmpty(originalFileName)) {
            page.addFilter(SearchFilter.build("originalFileName", SearchFilter.Operator.LIKE, originalFileName));
        }
        // 最后，它调用了“fileService”的“queryPage”方法来查询文件列表，并将结果通过“Rets.success”方法返回。
        page = fileService.queryPage(page);
        // 同时，该控制器类上面还加了一个“@RequiresPermissions”注解，表示该方法需要相应的权限才能访问。
        return Rets.success(page);
    }
}
