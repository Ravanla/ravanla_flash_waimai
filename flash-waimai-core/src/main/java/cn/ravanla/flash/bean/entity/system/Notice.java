package cn.ravanla.flash.bean.entity.system;

import cn.ravanla.flash.bean.entity.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.Table;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;

/**
 * Created  on 2020/4/2 0002.
 *
 *@Author ravanla
 */

@Entity(name = "t_sys_notice")
@Table(appliesTo = "t_sys_notice",comment = "通知")
@Data
@EntityListeners(AuditingEntityListener.class)
public class Notice extends BaseEntity {
    @Column
    private String title;
    @Column
    private Integer type;
    @Column
    private String content;

}
