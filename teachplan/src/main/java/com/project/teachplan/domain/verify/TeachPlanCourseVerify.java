package com.project.teachplan.domain.verify;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.teachplan.domain.base.BaseTeachPlanCourse;
import com.project.teachplan.domain.pk.TeachPlanCoursePk;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

import static com.project.base.common.keyword.Dic.VERIFY_STATUS_APPLY;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-9-27 10:13
 * @version: 1.0
 * @description:
 */
@Data
@Entity
@DynamicUpdate
@DynamicInsert
@org.hibernate.annotations.Table(appliesTo = "teach_plan_course_verify", comment = "教学计划课程审批管理")
@Table(name = "teach_plan_course_verify", indexes = {
        @Index(columnList = "course_id", name = "course_id_index"),
        @Index(columnList = "plan_id", name = "plan_id_index")
})
@EqualsAndHashCode(callSuper = true)
@IdClass(TeachPlanCoursePk.class)
@NoArgsConstructor
@AllArgsConstructor
public class TeachPlanCourseVerify extends BaseTeachPlanCourse implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    @JsonIgnore
    private TeachPlanCoursePk teachPlanCoursePk;

    @Column(name = "remark", columnDefinition = "VARCHAR(256) COMMENT '备注说明'")
    private String remark;

    @Column(name = "verify_status", nullable = false, columnDefinition = "CHAR(1) DEFAULT 1 COMMENT '审核状态 0 已经审核, 1 没有审核 2 拒绝'")
    private String verifyStatus = StrUtil.isBlank(this.verifyStatus) ? VERIFY_STATUS_APPLY : this.verifyStatus;

    public TeachPlanCourseVerify(String planId, String courseId, String courseName, String credit, Integer onLinePercentage,
                                 Integer linePercentage,
//                                 String teacherId, String teacherName,
                                 String centerAreaId, String remark, String userId, String verifyStatus, String type) {
        super(planId, courseId, courseName, credit, onLinePercentage, linePercentage,
//                teacherId, teacherName,
                centerAreaId, userId, type);
        this.remark = remark;
        this.verifyStatus = verifyStatus;
    }
}