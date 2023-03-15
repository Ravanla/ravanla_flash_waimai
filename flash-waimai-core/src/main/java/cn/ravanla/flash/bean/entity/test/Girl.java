package cn.ravanla.flash.bean.entity.test;

import cn.ravanla.flash.bean.entity.BaseEntity;
import org.hibernate.annotations.Table;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;
/**
 * 该实体用于测试生成代码
 */
@Entity(name="ttestgirl") // 定义实体类
@Table(appliesTo = "ttestgirl",comment = "女孩") // 定义表
public class Girl extends BaseEntity {

    @Column(columnDefinition = "VARCHAR(32) COMMENT '姓名'") // 定义字段类型及字段注释
    private String name;

    @Column(columnDefinition = "INT COMMENT '年龄'") // 定义字段类型及字段注释
    private Integer age;

    @Column(columnDefinition = "DATE COMMENT '生日'") // 定义字段类型及字段注释
    private Date birthday;

    @Column(name = "hasboyfriend",columnDefinition = "TINYINT COMMENT '是否有男朋友'") // 定义字段类型及字段注释
    private Boolean hasBoyFriend;
}