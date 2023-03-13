package cn.ravanla.flash.dao.message;


import cn.ravanla.flash.bean.entity.message.Message;
import cn.ravanla.flash.dao.BaseRepository;

import java.util.ArrayList;


public interface MessageRepository extends BaseRepository<Message,Long> {
    void deleteAllByIdIn(ArrayList<String> list);
}

