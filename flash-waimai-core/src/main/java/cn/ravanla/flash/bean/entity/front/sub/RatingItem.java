package cn.ravanla.flash.bean.entity.front.sub;

import lombok.Data;

/**
 * @Author ravanla
 * @date ：Created in 2021/10/24 23:25
 */
@Data
public class RatingItem {
    private Integer food_id;
    private String food_name;
    private String image_hash = "";
    private Integer is_valid = 1;
}
