package cn.ravanla.flash.api.controller.front.officialsite;

import cn.ravanla.flash.api.controller.BaseController;
import cn.ravanla.flash.bean.entity.cms.Contacts;
import cn.ravanla.flash.bean.vo.front.Rets;
import cn.ravanla.flash.service.cms.ContactsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/offcialsite/contact")
public class ContactController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ContactsService contactsService;

    @RequestMapping(method = RequestMethod.POST)
    public Object save(@Valid Contacts contacts){
        contactsService.insert(contacts);
        return Rets.success();
    }
}
