package cn.ravanla.flash.bean.vo.business;

import lombok.Data;

/**
 * 订单信息
 * @Author ravanla
 * @date ：Created in 2021/10/24 14:18
 */
@Data
public class OrderVo {
    private String address_id;
    private String come_from;
    private String deliver_time;
    private String description;
    private String geohash;
    private Long paymethod_id;
    private String sig;

}
