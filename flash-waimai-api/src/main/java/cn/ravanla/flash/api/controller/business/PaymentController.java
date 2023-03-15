package cn.ravanla.flash.api.controller.business;

import cn.ravanla.flash.api.controller.BaseController;
import cn.ravanla.flash.bean.vo.front.Rets;
import cn.ravanla.flash.utils.Maps;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：ravanla
 * @date ：Created in 2021/10/24 15:58
 */
// 处理与支付相关的请求
@RestController
@RequestMapping("/payapi/payment")

// 根据merchantId, merchantOrderNo, source, userId和version参数返回订单的查询结果，目前只返回一个固定的失败信息
public class PaymentController extends BaseController {
    @RequestMapping(value = "/queryOrder",method = RequestMethod.GET)
    public Object queryOrder(@RequestParam("merchantId") Long merchantId,
                             @RequestParam("merchantOrderNo") String merchantOrderNo,
                             @RequestParam("source") String source,
                             @RequestParam("userId") Long userId,
                             @RequestParam("version") String version){
        return Rets.success(
                Maps.newHashMap(
                        "status", 0,
                        "type","PAY_FAILED",
                        "message","暂不开放支付功能，2021年记录"
                )
        );
    }
}
