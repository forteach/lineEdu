package com.project.teachplan.domain.verify;

import cn.hutool.core.util.StrUtil;
import com.project.teachplan.domain.base.BaseTeachPlanClass;
import com.project.teachplan.domain.pk.TeachPlanClassPk;
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
 * @date: 19-9-27 11:15
 * @version: 1.0
 * @description:
 */
@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "teach_plan_class_verify", indexes = {
        @Index(columnList = "class_id", name = "class_id_index"),
        @Index(columnList = "plan_id", name = "plan_id_index")
})
@org.hibernate.annotations.Table(appliesTo = "teach_plan_class_verify", comment = "在线教学计划班级审计")
@EqualsAndHashCode(callSuper = true)
@IdClass(TeachPlanClassPk.class)
@NoArgsConstructor
@AllArgsConstructor
public class TeachPlanClassVerify extends BaseTeachPlanClass implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private TeachPlanClassPk teachPlanClassPk;

    @Column(name = "remark", columnDefinition = "VARCHAR(256) COMMENT '备注说明'")
    private String remark;

    @Column(name = "verify_status", nullable = false, columnDefinition = "CHAR(1) DEFAULT 1 COMMENT '审核状态 0 已经审核, 1 没有审核 2 拒绝'")
    private String verifyStatus = StrUtil.isBlank(this.verifyStatus) ? VERIFY_STATUS_APPLY : this.verifyStatus;

    public TeachPlanClassVerify(String classId, String planId, String className, String planName,
                                int classNumber, String centerAreaId, String remark, String userId, String verifyStatus) {
        super(classId, planId, className, planName, classNumber, centerAreaId, userId);
        this.remark = remark;
        this.verifyStatus = verifyStatus;
    }
}
