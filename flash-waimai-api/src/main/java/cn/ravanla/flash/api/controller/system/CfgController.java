package cn.ravanla.flash.api.controller.system;

import cn.ravanla.flash.api.controller.BaseController;
import cn.ravanla.flash.bean.constant.factory.PageFactory;
import cn.ravanla.flash.bean.core.BussinessLog;
import cn.ravanla.flash.bean.dictmap.CfgDict;
import cn.ravanla.flash.bean.entity.system.Cfg;
import cn.ravanla.flash.bean.entity.system.FileInfo;
import cn.ravanla.flash.bean.enumeration.BizExceptionEnum;
import cn.ravanla.flash.bean.enumeration.Permission;
import cn.ravanla.flash.bean.exception.ApplicationException;
import cn.ravanla.flash.bean.vo.front.Rets;
import cn.ravanla.flash.bean.vo.query.SearchFilter;
import cn.ravanla.flash.service.system.CfgService;
import cn.ravanla.flash.service.system.FileService;
import cn.ravanla.flash.service.system.LogObjectHolder;
import cn.ravanla.flash.utils.Maps;
import cn.ravanla.flash.utils.StringUtils;
import cn.ravanla.flash.utils.ToolUtil;
import cn.ravanla.flash.utils.factory.Page;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * CfgController
 *
 * @version 2020/11/17 0017
 * @Author ravanla
 */
@RestController
@RequestMapping("/cfg")
public class CfgController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private CfgService cfgService;
    @Autowired
    private FileService fileService;

    /**
     * 查询参数列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @RequiresPermissions(value = {Permission.CFG})
    public Object list(@RequestParam(required = false) String cfgName, @RequestParam(required = false) String cfgValue) {
        // 创建一个分页对象
        Page page = new PageFactory().defaultPage();

        // 如果cfgName不为空，添加一个筛选条件：cfgName LIKE cfgName
        if (StringUtils.isNotEmpty(cfgName)) {
            page.addFilter(SearchFilter.build("cfgName", SearchFilter.Operator.LIKE, cfgName));
        }

        // 如果cfgValue不为空，添加一个筛选条件：cfgValue LIKE cfgValue
        if (StringUtils.isNotEmpty(cfgValue)) {
            page.addFilter(SearchFilter.build("cfgValue", SearchFilter.Operator.LIKE, cfgValue));
        }

        // 调用cfgService的queryPage()方法，根据page对象的筛选条件获取分页数据
        page = cfgService.queryPage(page);

        // 返回包含分页数据的Rets对象
        return Rets.success(page);
    }

    /**
     * 导出参数列表
     *
     * @param cfgName
     * @param cfgValue
     * @return
     */
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    @RequiresPermissions(value = {Permission.CFG})
    public Object export(@RequestParam(required = false) String cfgName, @RequestParam(required = false) String cfgValue) {
        // 创建一个分页实例
        Page page = new PageFactory().defaultPage();

        // 如果cfgName不为空，则添加过滤器
        if (StringUtils.isNotEmpty(cfgName)) {
            page.addFilter(SearchFilter.build("cfgName", SearchFilter.Operator.LIKE, cfgName));
        }

        // 如果cfgValue不为空，则添加过滤器
        if (StringUtils.isNotEmpty(cfgValue)) {
            page.addFilter(SearchFilter.build("cfgValue", SearchFilter.Operator.LIKE, cfgValue));
        }

        // 查询分页结果
        page = cfgService.queryPage(page);

        // 使用模板创建Excel文件
        FileInfo fileInfo = fileService.createExcel("templates/config.xlsx", "系统参数.xlsx", Maps.newHashMap("list", page.getRecords()));

        // 返回成功信息
        return Rets.success(fileInfo);
    }

    @RequestMapping(method = RequestMethod.POST)
    @BussinessLog(value = "编辑参数", key = "cfgName", dict = CfgDict.class)
    @RequiresPermissions(value = {Permission.CFG_EDIT})
    public Object save(@ModelAttribute @Valid Cfg cfg) {
        if (cfg.getId() != null) {
            Cfg old = cfgService.get(cfg.getId());
            LogObjectHolder.me().set(old);
            old.setCfgName(cfg.getCfgName());
            old.setCfgValue(cfg.getCfgValue());
            old.setCfgDesc(cfg.getCfgDesc());
            cfgService.saveOrUpdate(old);
        } else {
            cfgService.saveOrUpdate(cfg);
        }
        return Rets.success();
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @BussinessLog(value = "删除参数", key = "id", dict = CfgDict.class)
    @RequiresPermissions(value = {Permission.CFG_DEL})
    public Object remove(@RequestParam Long id) {
        logger.info("id:{}", id);
        if (ToolUtil.isEmpty(id)) {
            throw new ApplicationException(BizExceptionEnum.REQUEST_NULL);
        }
        cfgService.delete(id);
        return Rets.success();
    }
}
