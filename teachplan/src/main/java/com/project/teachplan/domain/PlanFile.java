package com.project.teachplan.domain;

import com.project.mysql.domain.Entitys;
import com.project.teachplan.domain.base.BasePlanFile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 培训班级资料管理
 */
@Data
@Entity
@DynamicUpdate
@DynamicInsert
@org.hibernate.annotations.Table(appliesTo = "plan_file", comment = "在线计划资料详情表")
@Table(name = "plan_file", indexes = {
        @Index(columnList = "file_id", name = "file_id_index"),
        @Index(columnList = "plan_id", name = "plan_id_index")
})
@EqualsAndHashCode(callSuper = true)
public class PlanFile extends BasePlanFile implements Serializable {

}