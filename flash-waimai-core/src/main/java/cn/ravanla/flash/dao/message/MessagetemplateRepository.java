package cn.ravanla.flash.dao.message;


import cn.ravanla.flash.bean.entity.message.MessageTemplate;
import cn.ravanla.flash.dao.BaseRepository;

import java.util.List;


public interface MessagetemplateRepository extends BaseRepository<MessageTemplate,Long> {
    MessageTemplate findByCode(String code);

    List<MessageTemplate> findByIdMessageSender(Long idMessageSender);
}

