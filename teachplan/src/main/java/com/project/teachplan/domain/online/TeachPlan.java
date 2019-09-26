package com.project.teachplan.domain.online;

import com.project.mysql.domain.Entitys;
import com.project.teachplan.domain.base.BaseTeachPlan;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 培训项目计划列表管理
 */
@Data
@Entity
@DynamicUpdate
@DynamicInsert
@org.hibernate.annotations.Table(appliesTo = "teach_plan", comment = "在线培训项目管理")
@Table(name = "teach_plan", indexes = {@Index(columnList = "plan_id", name = "plan_id_index")})
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class TeachPlan extends BaseTeachPlan implements Serializable {

}