package cn.enilu.flash.api.controller.cms;

import cn.enilu.flash.api.controller.BaseController;
import cn.enilu.flash.bean.core.BussinessLog;
import cn.enilu.flash.bean.dictmap.CommonDict;
import cn.enilu.flash.bean.entity.cms.Channel;
import cn.enilu.flash.bean.enumeration.Permission;
import cn.enilu.flash.bean.vo.front.Rets;
import cn.enilu.flash.service.cms.ChannelService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 栏目管理
 * @RestController：注解用于标识Controller类，使其具有处理HTTP请求并返回响应的能力。
 * @RequestMapping：注解用于映射HTTP请求的URL路径到Controller的处理方法上。
 * @Autowired：注解用于自动装配依赖的bean对象。
 * @RequestParam：注解用于绑定HTTP请求中的参数到处理方法的参数上。
 * @Valid：注解用于指定需要进行数据校验的对象。
 * @BussinessLog：自定义注解，用于记录日志。
 * @RequiresPermissions：注解用于进行权限控制。
 * PageFactory：自定义工具类，用于创建分页对象。
 * SearchFilter：自定义类，用于封装查询条件。
 * Rets：自定义类，用于封装响应结果。
 */
// @RestController注解标记为RESTful接口，
@RestController
// 通过@RequestMapping注解将该类映射到“/channel”的URL路径下。
@RequestMapping("/channel")
public class ChannelMgrController extends BaseController {
    // 类中使用@Autowired注解自动注入了一个ChannelService的实例，并定义了三个方法：
    @Autowired
    private ChannelService channelService;

    @RequestMapping(method = RequestMethod.POST)
    // 该方法同时使用了@BussinessLog注解进行业务日志记录，记录操作类型为“编辑栏目”，操作对象为栏目名称，操作结果字典为CommonDict类中的字典值，
    @BussinessLog(value = "编辑栏目", key = "name", dict = CommonDict.class)
    // 并通过@RequiresPermissions注解进行权限控制，只有具有CHANNEL_EDIT权限的用户才能访问。
    @RequiresPermissions(value = {Permission.CHANNEL_EDIT})
    // save方法：用于保存栏目信息，接收一个Channel对象，通过@ModelAttribute注解将请求参数绑定到该对象上，并通过@Valid注解进行校验。
    public Object save(@ModelAttribute @Valid Channel channel) {
        if(channel.getId()==null){ // 在方法中判断该栏目对象是否存在，如果不存在，则调用insert方法进行添加，
            channelService.insert(channel);
        }else{ // 如果存在，则调用update方法进行更新。
            channelService.update(channel);
        }
        return Rets.success();
    }

    @RequestMapping(method = RequestMethod.DELETE)
    // 该方法同时使用了@BussinessLog注解进行业务日志记录，记录操作类型为“删除栏目”，操作对象为栏目id，操作结果字典为CommonDict类中的字典值，
    @BussinessLog(value = "删除栏目", key = "id", dict = CommonDict.class)
    // 并通过@RequiresPermissions注解进行权限控制，只有具有CHANNEL_DEL权限的用户才能访问。
    @RequiresPermissions(value = {Permission.CHANNEL_DEL})
    // remove方法：用于删除栏目信息，接收一个Long类型的id参数，调用channelService的delete方法进行删除操作。
    public Object remove(Long id) {
        channelService.delete(id);
        return Rets.success();
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    // 该方法同时使用了@RequiresPermissions注解进行权限控制，只有具有CHANNEL权限的用户才能访问。
    @RequiresPermissions(value = {Permission.CHANNEL})
    // list方法：用于查询所有的栏目信息，调用channelService的queryAll方法进行查询操作，并将结果以List类型返回。
    public Object list() {
        List<Channel> list = channelService.queryAll();
        // 最后，将查询结果通过Rets.success方法进行封装，并以JSON格式返回给前端。
        return Rets.success(list);
    }
}
