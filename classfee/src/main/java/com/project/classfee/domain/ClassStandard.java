package com.project.classfee.domain;


import com.project.mysql.domain.Entitys;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.Id;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 课时费标准
 */
@Data
@Entity
@DynamicUpdate
@DynamicInsert
@org.hibernate.annotations.Table(appliesTo = "class_standard", comment = "课时费标准")
@Table(name = "class_standard")
@EqualsAndHashCode(callSuper = true)
public class ClassStandard extends Entitys {

    @Id
    @Column(name = "standard_id", columnDefinition = "VARCHAR(32) COMMENT '课时费编号'")
    private String standardId;

    @Column(name = "create_year", columnDefinition = "VARCHAR(32) COMMENT '创建所属的学年'")
    private String createYear;

    @Column(name = "student_sum", columnDefinition = "VARCHAR(32) COMMENT '学生总人数'")
    private int studentSum;

    @Column(name = "student_subsidies", columnDefinition = "VARCHAR(32) COMMENT '每人补贴金额'")
    private int studentSubsidies;

    @Column(name = "subsidies_sum", columnDefinition = "VARCHAR(32) COMMENT '中心补贴总金额'")
    private int subsidiesSum;

    @Column(name = "class_fee", columnDefinition = "VARCHAR(32) COMMENT '每节课的课时费'")
    private int class_fee;

    public ClassStandard(String standardId, String createYear, int studentSum, int studentSubsidies, int subsidiesSum, int class_fee,String centerId) {
        this.standardId = standardId;
        this.createYear = createYear;
        this.studentSum = studentSum;
        this.studentSubsidies = studentSubsidies;
        this.subsidiesSum = subsidiesSum;
        this.class_fee = class_fee;
        super.setCenterAreaId(centerId);
    }

    public ClassStandard(){

    }
}
