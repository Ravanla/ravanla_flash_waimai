package cn.ravanla.flash.api.controller.system;

import cn.ravanla.flash.api.controller.BaseController;
import cn.ravanla.flash.bean.entity.system.Notice;
import cn.ravanla.flash.bean.vo.front.Rets;
import cn.ravanla.flash.service.system.NoticeService;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * NoticeController
 *
 *@Author ravanla
 * @version 2020/12/17 0017
 */
@RestController
@RequestMapping("/notice")
public class NoticeController extends BaseController {
    @Autowired
    private NoticeService noticeService;
    /**
     * 获取通知列表
     */
    @RequestMapping(value = "/list")
    public Object list(String condition) {
        List<Notice> list = null;
        if(Strings.isNullOrEmpty(condition)) {
            list =  noticeService.queryAll();
        }else{
            list = noticeService.findByTitleLike(condition);
        }
        return Rets.success(list);
    }
}
