package com.project.databank.domain;

import com.project.mysql.domain.Entitys;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Description: 选择题
 * @author: liu zhenming
 * @version: V1.0
 * @date: 2018/11/7 17:15
 */
@Data
@Entity
@Table(name = "choice_qst", indexes = {@Index(columnList = "course_id", name = "course_id_index")})
@EqualsAndHashCode(callSuper = true)
@org.hibernate.annotations.Table(appliesTo = "choice_qst", comment = "选择题")
@AllArgsConstructor
@NoArgsConstructor
public class ChoiceQst extends Entitys implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @GeneratedValue(generator = "system-uuid")
    @Column(name = "choice_qst_id", columnDefinition = "VARCHAR(32) COMMENT '试题编号'")
    private String choiceQstId;

    @Column(name = "course_id", columnDefinition = "VARCHAR(32) COMMENT '科目编号'")
    private String courseId;

    @Column(name = "choice_qst_txt", columnDefinition = "VARCHAR(500) COMMENT '试题内容'")
    private String choiceQstTxt;

    @Column(name = "choice_qst_answ", columnDefinition = "VARCHAR(20) COMMENT '答案'")
    private String choiceQstAnsw;

    @Column(name = "choice_type", columnDefinition = "VARCHAR(10) COMMENT '选择题类型'")
    private String choiceType;
}
