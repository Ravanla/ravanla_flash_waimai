package cn.ravanla.flash.bean.entity.system;


import cn.ravanla.flash.bean.entity.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.Table;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity(name = "tsystask")
@Table(appliesTo = "tsystask",comment = "定时任务")
@Data
@EntityListeners(AuditingEntityListener.class)
public class Task extends BaseEntity {

    // 任务名
    @Column(columnDefinition = "VARCHAR(50) COMMENT '任务名'")
    @NotBlank(message = "名称不能为空")
    private String name;

    // 任务组名
    @Column(name="job_group", columnDefinition = "VARCHAR(50) COMMENT '任务组名'")
    private String jobGroup;

    // 执行类
    @Column(name="job_class", columnDefinition = "VARCHAR(255) COMMENT '执行类'")
    @NotBlank(message = "执行类不能为空")
    private String jobClass;

    // 任务说明
    @Column(name="note", columnDefinition = "VARCHAR(255) COMMENT '任务说明'")
    private String note;

    // 定时规则
    @Column(name="cron", columnDefinition = "VARCHAR(50) COMMENT '定时规则'")
    @NotBlank(message = "定时规则不能为空")
    private String cron;

    // 是否允许并发
    @Column(name="concurrent", columnDefinition = "TINYINT COMMENT '是否允许并发'")
    private boolean concurrent;

    // 执行参数
    @Column(name="data", columnDefinition = "TEXT COMMENT '执行参数'")
    private String data;

    // 执行时间
    @Column(name="exec_at", columnDefinition = "DateTime COMMENT '执行时间'")
    private Date execAt;

    // 执行结果
    @Column(name="exec_result", columnDefinition = "text COMMENT '执行结果'")
    private String execResult;

    // 是否禁用
    @Column(name="disabled", columnDefinition = "TINYINT COMMENT '是否禁用'")
    private boolean disabled;
}


//@Entity(name = "t_sys_task")
//@Table(appliesTo = "t_sys_task",comment = "定时任务")
//@Data
//@EntityListeners(AuditingEntityListener.class)
//public class Task extends BaseEntity {
//
//    @Column(columnDefinition = "VARCHAR(50) COMMENT '任务名'")
//    @NotBlank(message = "名称不能为空")
//    private String name;
//
//    @Column(name="job_group", columnDefinition = "VARCHAR(50) COMMENT '任务组名'")
//    private String jobGroup;
//
//    @Column(name="job_class", columnDefinition = "VARCHAR(255) COMMENT '执行类'")
//    @NotBlank(message = "执行类不能为空")
//    private String jobClass;
//
//    @Column(name="note", columnDefinition = "VARCHAR(255) COMMENT '任务说明'")
//    private String note;
//
//    @Column(name="cron", columnDefinition = "VARCHAR(50) COMMENT '定时规则'")
//    @NotBlank(message = "定时规则不能为空")
//    private String cron;
//
//    @Column(name="concurrent", columnDefinition = "TINYINT COMMENT '是否允许并发'")
//    private boolean concurrent;
//
//    @Column(name="data", columnDefinition = "TEXT COMMENT '执行参数'")
//    private String data;
//
//    @Column(name="exec_at", columnDefinition = "DateTime COMMENT '执行时间'")
//    private Date execAt;
//
//    @Column(name="exec_result", columnDefinition = "text COMMENT '执行结果'")
//    private String execResult;
//
//    @Column(name="disabled", columnDefinition = "TINYINT COMMENT '是否禁用'")
//    private boolean disabled;
//}
