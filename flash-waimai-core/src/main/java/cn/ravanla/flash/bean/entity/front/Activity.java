package cn.ravanla.flash.bean.entity.front;

import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created  on 2020/1/4 0004.
 *
 *@Author ravanla
 */
@Document(collection = "activities")
public class Activity extends BaseMongoEntity {
    // 必须在setget方法加上该注释，否则_id值会覆盖在id上
    @Id
    private String _id;
    private Long id;
    private String name;
    private String description;
    private String icon_color;
    private String icon_name;
    private Integer ranking_weight;
//    @JSONField(name="_id")
//    public String get_id() {
//        return _id;
//    }
//    @JSONField(name="_id")
//    public void set_id(String _id) {
//        this._id = _id;
//    }
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public String getIcon_color() {
//        return icon_color;
//    }
//
//    public void setIcon_color(String icon_color) {
//        this.icon_color = icon_color;
//    }
//
//    public String getIcon_name() {
//        return icon_name;
//    }
//
//    public void setIcon_name(String icon_name) {
//        this.icon_name = icon_name;
//    }
//
//    public Integer getRanking_weight() {
//        return ranking_weight;
//    }
//
//    public void setRanking_weight(Integer ranking_weight) {
//        this.ranking_weight = ranking_weight;
//    }

    @JSONField(name="_id") // 指定JSON序列化和反序列化时使用的名称
    public String get_id() { // 获取 _id 属性值的方法
        return _id;
    }

    @JSONField(name="_id") // 指定JSON序列化和反序列化时使用的名称
    public void set_id(String _id) { // 设置 _id 属性值的方法
        this._id = _id;
    }

    public Long getId() { // 获取 id 属性值的方法
        return id;
    }

    public void setId(Long id) { // 设置 id 属性值的方法
        this.id = id;
    }

    public String getName() { // 获取 name 属性值的方法
        return name;
    }

    public void setName(String name) { // 设置 name 属性值的方法
        this.name = name;
    }

    public String getDescription() { // 获取 description 属性值的方法
        return description;
    }

    public void setDescription(String description) { // 设置 description 属性值的方法
        this.description = description;
    }

    public String getIcon_color() { // 获取 icon_color 属性值的方法
        return icon_color;
    }

    public void setIcon_color(String icon_color) { // 设置 icon_color 属性值的方法
        this.icon_color = icon_color;
    }

    public String getIcon_name() { // 获取 icon_name 属性值的方法
        return icon_name;
    }

    public void setIcon_name(String icon_name) { // 设置 icon_name 属性值的方法
        this.icon_name = icon_name;
    }

    public Integer getRanking_weight() { // 获取 ranking_weight 属性值的方法
        return ranking_weight;
    }

    public void setRanking_weight(Integer ranking_weight) { // 设置 ranking_weight 属性值的方法
        this.ranking_weight = ranking_weight;
    }
}
