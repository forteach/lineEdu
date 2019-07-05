package com.project.schoolroll.service;

import com.project.schoolroll.domain.Family;
import com.project.schoolroll.dto.FamilyDto;

import java.util.List;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-7-4 09:52
 * @version: 1.0
 * @description:
 */
public interface FamilyService {
    /**　
     * by 学生id
     * 查询精简字段家庭成员信息
     * @param stuId
     * @return
     */
    List<FamilyDto> findFamilyDtoList(String stuId);

    /**
     * 查询全部字段家庭成员信息
     * @param stuId
     * @return
     */
    List<Family> findFamilies(String stuId);

    void removeFamilyById(String familyId);
}
