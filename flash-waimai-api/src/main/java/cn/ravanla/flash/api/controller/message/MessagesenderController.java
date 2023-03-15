package cn.ravanla.flash.api.controller.message;

import cn.ravanla.flash.bean.constant.factory.PageFactory;
import cn.ravanla.flash.bean.core.BussinessLog;
import cn.ravanla.flash.bean.dictmap.CommonDict;
import cn.ravanla.flash.bean.entity.message.MessageSender;
import cn.ravanla.flash.bean.enumeration.Permission;
import cn.ravanla.flash.bean.vo.front.Rets;
import cn.ravanla.flash.service.message.MessagesenderService;
import cn.ravanla.flash.utils.factory.Page;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/message/sender")
public class MessagesenderController {
    @Autowired
    private MessagesenderService messagesenderService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @RequiresPermissions(value = {Permission.MSG_SENDER})
    public Object list() {
        // 创建一个MessageSender类型的Page对象
        Page page = new PageFactory().defaultPage();
        // 调用messagesenderService的queryPage方法查询结果
        page = messagesenderService.queryPage(page);
        // 将查询到的记录设置到page对象中
        page.setRecords(page.getRecords());
        // 返回成功的page结果
        return Rets.success(page);
    }

    @RequestMapping(value = "/queryAll", method = RequestMethod.GET)
    @RequiresPermissions(value = {Permission.MSG_SENDER})
    public Object queryAll() {
        return Rets.success(messagesenderService.queryAll());
    }

    @RequestMapping(method = RequestMethod.POST)
    @BussinessLog(value = "编辑消息发送者", key = "name", dict = CommonDict.class)
    @RequiresPermissions(value = {Permission.MSG_SENDER_EDIT})
    // 最后使用@ModelAttribute和@Valid注解，以实体类MessageSender作为参数，
    // 调用messagesenderService.save()方法保存实体，
    // 最终返回Rets.success()，表示操作成功。
    public Object save(@ModelAttribute @Valid MessageSender tMessageSender) {
        messagesenderService.save(tMessageSender);
        return Rets.success();
    }


    @RequestMapping(method = RequestMethod.DELETE)
    @BussinessLog(value = "删除消息发送者", key = "id", dict = CommonDict.class)
    @RequiresPermissions(value = {Permission.MSG_SENDER_DEL})
    public Object remove(Long id) {

        try {
            messagesenderService.delete(id);
            return Rets.success();
        } catch (Exception e) {
            return Rets.failure(e.getMessage());
        }

    }
}