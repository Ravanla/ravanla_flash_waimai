package cn.ravanla.flash.api.controller.message;

import cn.ravanla.flash.bean.constant.factory.PageFactory;
import cn.ravanla.flash.bean.core.BussinessLog;
import cn.ravanla.flash.bean.dictmap.CommonDict;
import cn.ravanla.flash.bean.entity.message.MessageTemplate;
import cn.ravanla.flash.bean.enumeration.BizExceptionEnum;
import cn.ravanla.flash.bean.enumeration.Permission;
import cn.ravanla.flash.bean.exception.ApplicationException;
import cn.ravanla.flash.bean.vo.front.Rets;
import cn.ravanla.flash.service.message.MessagetemplateService;
import cn.ravanla.flash.utils.ToolUtil;
import cn.ravanla.flash.utils.factory.Page;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/message/template")
public class MessagetemplateController {
    @Autowired
    private MessagetemplateService messagetemplateService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @RequiresPermissions(value = {Permission.MSG_TPL})
    public Object list() {
        Page<MessageTemplate> page = new PageFactory<MessageTemplate>().defaultPage();
        page = messagetemplateService.queryPage(page);
        page.setRecords(page.getRecords());
        return Rets.success(page);
    }

    @RequestMapping(method = RequestMethod.POST)
    @BussinessLog(value = "编辑消息模板", key = "name", dict = CommonDict.class)
    @RequiresPermissions(value = {Permission.MSG_TPL_EDIT})
    public Object save(@ModelAttribute @Valid MessageTemplate tMessageTemplate) {
        if(tMessageTemplate.getId()!=null)
        {
            messagetemplateService.update(tMessageTemplate);
        }else{
            messagetemplateService.insert(tMessageTemplate);
        }
        return Rets.success();
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @BussinessLog(value = "删除消息模板", key = "id", dict = CommonDict.class)
    @RequiresPermissions(value = {Permission.MSG_TPL_DEL})
    public Object remove(Long id) {
        if (ToolUtil.isEmpty(id)) {
            throw new ApplicationException(BizExceptionEnum.REQUEST_NULL);
        }
        messagetemplateService.delete(id);
        return Rets.success();
    }
}