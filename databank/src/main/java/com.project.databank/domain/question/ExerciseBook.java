package com.project.databank.domain.question;

import com.project.mysql.domain.Entitys;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Description: 练习册
 * @author: liu zhenming
 * @version: V1.0
 * @date: 2018/11/7 9:31
 */
@Data
@Entity
@Table(name = "exercise_book", indexes = {@Index(columnList = "teacher_id", name = "teacher_id_index"),
        @Index(columnList = "course_id", name = "course_id_index")})
@EqualsAndHashCode(callSuper = true)
@org.hibernate.annotations.Table(appliesTo = "exercise_book", comment = "练习册")
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseBook extends Entitys implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @GeneratedValue(generator = "system-uuid")
    @Column(name = "exe_book_id", columnDefinition = "varchar(32) COMMENT '练习册编号 主键'")
    private String exeBookId;

    @Column(name = "exe_book_type", columnDefinition = "int COMMENT '练习册类型'")
    private Integer exeBookType;

    @Column(name = "teacher_id", columnDefinition = "varchar(32) COMMENT '创建教师'")
    private String teacherId;

    @Column(name = "course_id", columnDefinition = "varchar(32) COMMENT '科目编号'")
    private String courseId;

    @Column(name = "exe_book_name", columnDefinition = "varchar(255) COMMENT '练习册名称'")
    private String exeBookName;
}