package cn.ravanla.flash.service.system;

import cn.ravanla.flash.bean.entity.system.Notice;
import cn.ravanla.flash.dao.system.NoticeRepository;
import cn.ravanla.flash.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * descript
 *
 * @Author ravanla
 * @date ：Created in 2021/6/30 11:14
 */
@Service
public class NoticeService extends BaseService<Notice,Long, NoticeRepository> {
    @Autowired
    private NoticeRepository noticeRepository;
    public List<Notice> findByTitleLike(String title) {
        return noticeRepository.findByTitleLike("%"+title+"%");
    }
}
