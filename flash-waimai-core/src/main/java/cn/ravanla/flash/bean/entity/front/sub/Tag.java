package cn.ravanla.flash.bean.entity.front.sub;

import lombok.Data;

/**
 * @Author ravanla
 * @date ：Created in 2021/10/24 23:21
 */
@Data
public class Tag {
    private Integer count = 0;
    private String name;
    private Boolean unsatisfied=false;
}
