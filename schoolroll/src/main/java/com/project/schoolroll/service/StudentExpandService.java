package com.project.schoolroll.service;

import com.project.schoolroll.dto.StudentExpandDto;
import com.project.schoolroll.web.vo.StudentExpandVo;

import java.util.List;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-7-8 15:45
 * @version: 1.0
 * @description:
 */
public interface StudentExpandService {
    public List<StudentExpandDto> findStudentExpandInfo(String stuId);

    int deleteAllStudentExpandByStuId(String stuId);

    void deleteById(String expandId);

    void saveUpdateStudentExpand(StudentExpandVo studentExpandVo);
}
