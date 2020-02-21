package com.project.classfee.service.po;


import com.project.mysql.domain.Entitys;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 课时费管理明细
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassInfoImpFile extends Entitys implements Serializable {

    private static final long serialVersionUID = 1L;

    private String serialId;

    private String classFeeId;

    private String fullName;

    private String createYear;

    private String createMonth;

    private String specialtyIds;

    private int classFee;

    private int classCount;

    public ClassInfoImpFile(String serialId, String classFeeId, String fullName, String createYear, String createMonth, String specialtyIds, int classFee, int classCount, String centerId) {
        this.serialId = serialId;
        this.classFeeId = classFeeId;
        this.fullName = fullName;
        this.createYear = createYear;
        this.createMonth = createMonth;
        this.specialtyIds = specialtyIds;
        this.classFee = classFee;
        this.classCount = classCount;
        super.setCenterAreaId(centerId);
    }
}
