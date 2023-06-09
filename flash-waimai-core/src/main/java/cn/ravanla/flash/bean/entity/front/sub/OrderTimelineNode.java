package cn.ravanla.flash.bean.entity.front.sub;

import lombok.Data;

import java.util.List;

/**
 * Created  on 2020/1/7 0007.
 *
 *@Author ravanla
 */
@Data
public class OrderTimelineNode {
    private List actions;
    private String description;
    private String sub_description;
    private String title;
    private Integer in_processing;

}
