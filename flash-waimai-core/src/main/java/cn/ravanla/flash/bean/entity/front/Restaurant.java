package cn.ravanla.flash.bean.entity.front;

import com.mongodb.client.model.geojson.Point;
import org.springframework.data.annotation.Id;

public class Restaurant {
    @Id
    private String id;

    private String name;

    private Address address;

    private Point location;

    // getters and setters
}
