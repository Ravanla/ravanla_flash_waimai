package cn.ravanla.flash.bean.entity.system;

import cn.ravanla.flash.bean.entity.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.Table;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import java.util.Date;

/**
 * Created  on 2020/4/2 0002.
 *
 *@Author ravanla
 */
@Entity(name = "t_sys_user")
@Table(appliesTo = "t_sys_user",comment = "账号")
@Data
@EntityListeners(AuditingEntityListener.class)
public class User  extends BaseEntity {
    @Column
    private String avatar;
    @Column
    private String account;
    @Column
    private String password;
    // 盐值 用于用户加密的
    @Column
    private String salt;
    @Column
    private String name;
    @Column
    private Date birthday;
    @Column
    private Integer sex;
    @Column
    private String email;
    @Column
    private String phone;
    @Column
    private String roleid;
    @Column
    private Long deptid;
    @Column
    private Integer status;
    @Column
    private Integer version;
}
