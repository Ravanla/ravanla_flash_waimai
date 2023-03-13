package cn.ravanla.flash.bean.entity.front;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
/**
 * Created  on 2021/10/09
 *
 *@Author ravanla
 */
@Data
@Document(collection = "users")
public class FrontUser extends BaseMongoEntity{
    @Id
    private String _id;
    private String username;
    private String password;
    private Long user_id;
    /**
     * 小程序openid
     */
    private String miniappOpenid;

}