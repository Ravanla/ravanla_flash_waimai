package cn.ravanla.flash.bean.entity.system;

import cn.ravanla.flash.bean.entity.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.Table;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.validation.constraints.NotBlank;

/**
 * Created on 2020/4/2 0002.
 *
 * @Author ravanla
 */
@Entity(name = "t_sys_cfg")
@Table(appliesTo = "t_sys_cfg", comment = "系统参数")
@Data
@EntityListeners(AuditingEntityListener.class)
public class Cfg extends BaseEntity {
    // 声明一个用于验证字符串非空的注解，若为空，则返回定义的错误信息
    // 定义该属性对应数据库中的列名和列的属性，
    // 其中 columnDefinition 可以指定列的详细属性，如数据类型、注释等
    @NotBlank(message = "参数名并能为空")
    @Column(name = "cfg_name", columnDefinition = "VARCHAR(256) COMMENT '参数名'")
    private String cfgName;

    // 声明一个用于验证字符串非空的注解，若为空，则返回定义的错误信息
    // 定义该属性对应数据库中的列名和列的属性，
    @NotBlank(message = "参数值不能为空")
    @Column(name = "cfg_value", columnDefinition = "VARCHAR(512) COMMENT '参数值'")
    private String cfgValue;

    // 定义该属性对应数据库中的列名和列的属性，
    @Column(name = "cfg_desc", columnDefinition = "TEXT COMMENT '备注'")
    private String cfgDesc;

}
