package cn.ravanla.flash.bean.entity.front.sub;

import cn.ravanla.flash.utils.Maps;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created  on 2020/1/5 0005.
 *
 *@Author ravanla
 */
@Data
public class OrderBasket {

//    private List<OrderFee> abandoned_extra = new ArrayList<OrderFee>();
//    private OrderFee deliver_fee = new OrderFee();
//    private Map packing_fee = Maps.newHashMap();
//    private List extra = new ArrayList();
//    private List pindan_map = new ArrayList();
//    private List<List<OrderItem>> group = new ArrayList<List<OrderItem>>();

    private List<OrderFee> abandoned_extra = new ArrayList<OrderFee>(); //创建一个OrderFee泛型的List，用于存放废弃的额外费用
    private OrderFee deliver_fee = new OrderFee(); //创建一个OrderFee的实例，用于存放配送费
    private Map packing_fee = Maps.newHashMap(); //创建一个Map，用于存放包装费
    private List extra = new ArrayList(); //创建一个List，用于存放额外费用
    private List pindan_map = new ArrayList(); //创建一个List，用于存放拼单信息
    private List<List<OrderItem>> group = new ArrayList<List<OrderItem>>(); //创建一个OrderItem泛型的List，用于存放订单分组信息


}
