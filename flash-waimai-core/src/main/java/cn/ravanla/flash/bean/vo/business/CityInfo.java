package cn.ravanla.flash.bean.vo.business;

import lombok.Data;

import java.io.Serializable;

/**
 * Created  on 2021/12/29 0029.
 *
 *@Author ravanla
 */
@Data
public class CityInfo implements Serializable {
    private String lat;
    private String lng;
    private String city;
}
