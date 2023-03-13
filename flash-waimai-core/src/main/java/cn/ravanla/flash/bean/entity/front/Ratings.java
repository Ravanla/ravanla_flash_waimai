package cn.ravanla.flash.bean.entity.front;

import cn.ravanla.flash.bean.entity.front.sub.Rating;
import cn.ravanla.flash.bean.entity.front.sub.Score;
import cn.ravanla.flash.bean.entity.front.sub.Tag;
import cn.ravanla.flash.utils.Lists;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * @Author ravanla
 * @date ：Created in 2021/10/24 23:19
 */
@Data
@Document(collection = "ratings")
public class Ratings extends BaseMongoEntity {
    private Long restaurant_id;
    private List<Rating> ratings = Lists.newArrayList(new Rating());
    private List<Tag> tags = Lists.newArrayList(new Tag());
    private Score scores = new Score();

    public Ratings(){

    }
    public Ratings(Long restaurant_id){
        this.restaurant_id = restaurant_id;
    }
}
