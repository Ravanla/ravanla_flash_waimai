package cn.ravanla.flash.bean.entity.front.sub;

import lombok.Data;

/**
 * Created  on 2020/1/5 0005.
 *
 *@Author ravanla
 */
@Data
public class OrderFee {
    private Long category_id;
    private String name;
    private Double price;
    private Double quantity;

}
