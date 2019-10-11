package com.project.course.domain.ziliao;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

;


/**
 * @Description:　图集图片
 * @author: liu zhenming
 * @version: V1.0
 * @date: 2018/11/6 15:45
 */
@Data
@Entity
@Builder
@Table(name = "photos", indexes = {
        @Index(columnList = "chapter_id", name = "chapter_id_index"),
        @Index(columnList = "arlits_id", name = "arlits_id_index"),
        @Index(name = "file_id_index", columnList = "file_id")
})
@org.hibernate.annotations.Table(appliesTo = "photos", comment = "图片")
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Photos extends AbsFile {

    /**
     * 图集编号
     */
    @Column(name = "arlits_id", columnDefinition = "VARCHAR(32) COMMENT '图集编号'")
    private String arlitsId;
}
