package com.project.schoolroll.repository;

import com.project.schoolroll.domain.StudentExpand;
import com.project.schoolroll.repository.dto.StudentExpandDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    @Transactional(readOnly = true)
    StudentExpand findAllByIsValidatedEqualsAndStuId(String isValidated, String stuId);

    /**
     * 查询有效的学生扩展信息
     * @param stuId
     * @return
     */
    @Transactional(readOnly = true)
    @Query(value = "SELECT expandId AS expandId, expandName AS expandName, expandValue AS expandValue FROM StudentExpand WHERE isValidated = '0' AND stuId = ?1")
    List<StudentExpandDto> findByIsValidatedEqualsAndStuId(String stuId);

    /**
     * 删除扩展信息根据学生id
     * @param stuId
     * @return
     */
    @Modifying(clearAutomatically = true)
    int deleteAllByStuId(String stuId);

    /**
     * 根据学生id查询对应的扩展信息
     * @param stuId
     * @param expandName
     * @return
     */
    @Transactional(readOnly = true)
    List<StudentExpand> findAllByStuIdAndExpandName(String stuId, String expandName);
}
