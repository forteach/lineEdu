package com.project.schoolroll.repository;

import com.project.schoolroll.domain.StudentExpand;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-7-2 17:05
 * @version: 1.0
 * @description:
 */
public interface StudentExpandRepository extends JpaRepository<StudentExpand, String> {

    /**
     * 查询扩展信息
     * @param isValidated
     * @param stuId
     * @return
     */
    StudentExpand findAllByIsValidatedEqualsAndStuId(String isValidated, String stuId);
}
