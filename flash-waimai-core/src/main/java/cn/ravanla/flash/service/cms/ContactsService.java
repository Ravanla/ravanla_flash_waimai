package cn.ravanla.flash.service.cms;

import cn.ravanla.flash.bean.entity.cms.Contacts;
import cn.ravanla.flash.dao.cms.ContactsRepository;
import cn.ravanla.flash.service.BaseService;
import org.springframework.stereotype.Service;

@Service
public class ContactsService extends BaseService<Contacts,Long, ContactsRepository> {
}
