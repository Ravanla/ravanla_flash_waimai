package cn.ravanla.flash.bean.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;



/**
 * Created  on 2021/1/8 0002.
 *
 *@Author ravanla
 */
/*

* @MappedSuperclass注解表示该类是一个父类映射类，用于标注父类，子类将继承父类映射属性。

* @Data注解是lombok插件提供的注解，可以自动生成类的get、set、toString等方法。

* public abstract修饰符表示这是一个抽象类。

* Serializable接口表示该类支持序列化。
*/
@MappedSuperclass
@Data
public abstract class BaseEntity implements Serializable {

    /*

    @Id注解表示该属性是主键，@GeneratedValue注解表示主键自动生成。
    */
    @Id
    @GeneratedValue
    private Long id;
    /*

    @CreatedDate注解表示该属性在创建时自动生成时间，
    @Column注解可以自定义数据库表字段名、类型、注释等信息。
    */
    @CreatedDate
    @Column(name = "create_time",columnDefinition="DATETIME COMMENT '创建时间/注册时间'")
    private Date createTime;
    /*

    @CreatedBy注解表示该属性在创建时自动生成创建人id，
    @Column注解可以自定义数据库表字段名、类型、注释等信息。
    */
    @Column(name = "create_by",columnDefinition="bigint COMMENT '创建人'")
    @CreatedBy
    private Long createBy;
    /*

    @LastModifiedDate注解表示该属性在修改时自动生成时间，
    @Column注解可以自定义数据库表字段名、类型、注释等信息。
    */
    @LastModifiedDate
    @Column(name = "modify_time",columnDefinition="DATETIME COMMENT '最后更新时间'")
    private Date modifyTime;
    /*

    @LastModifiedBy注解表示该属性在修改时自动生成最后修改人id，
    @Column注解可以自定义数据库表字段名、类型、注释等信息。
    */
    @LastModifiedBy
    @Column(name = "modify_by",columnDefinition="bigint COMMENT '最后更新人'")
    private Long modifyBy;
}
