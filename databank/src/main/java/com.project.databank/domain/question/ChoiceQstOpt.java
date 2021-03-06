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
 * @Description:　选择题选项
 * @author: liu zhenming
 * @version: V1.0
 * @date: 2018/11/8 9:29
 */
@Data
@Entity
@Table(name = "choice_qst_opt", indexes = {@Index(columnList = "choice_qst_id", name = "choice_qst_id_index")})
@EqualsAndHashCode(callSuper = true)
@org.hibernate.annotations.Table(appliesTo = "choice_qst_opt", comment = "选择题选项")
@AllArgsConstructor
@NoArgsConstructor
public class ChoiceQstOpt extends Entitys implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @GeneratedValue(generator = "system-uuid")
    @Column(name = "choice_qst_id", columnDefinition = "varchar(32) COMMENT '试题编号'")
    private String choiceQstId;

    @Column(name = "opt_id", columnDefinition = "varchar(32) COMMENT '选项ID'")
    private String optId;

    @Column(name = "opt_txt", columnDefinition = "varchar(255) COMMENT '选项描述'")
    private String optTxt;

    @Column(name = "opt_value", columnDefinition = "varchar(2) COMMENT '选项值'")
    private String optValue;
}
