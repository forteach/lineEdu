//package com.project.schoolroll.repository;
//
//import com.project.schoolroll.domain.StudentExpand;
//import com.project.schoolroll.repository.dto.StudentExpandDto;
//import com.project.schoolroll.repository.dto.StudentExpandExportDto;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
///**
// * @author: zhangyy
// * @email: zhang10092009@hotmail.com
// * @date: 19-7-2 17:05
// * @version: 1.0
// * @description:
// */
//public interface StudentExpandRepository extends JpaRepository<StudentExpand, String> {
//
//    /**
//     * 查询扩展信息
//     * @param isValidated
//     * @param studentId
//     * @return
//     */
//    @Transactional(readOnly = true)
//    StudentExpand findAllByIsValidatedEqualsAndStudentId(String isValidated, String studentId);
//
//    /**
//     * 查询有效的学生扩展信息
//     * @param studentId
//     * @return
//     */
//    @Transactional(readOnly = true)
//    @Query(value = "SELECT expandId AS expandId, expandName AS expandName, expandValue AS expandValue, expandExplain AS expandExplain FROM StudentExpand WHERE isValidated = '0' AND studentId = ?1")
//    List<StudentExpandDto> findByIsValidatedEqualsAndStudentId(String studentId);
//
//    /**
//     * 删除扩展信息根据学生id
//     * @param studentId
//     * @return
//     */
//    @Modifying(clearAutomatically = true)
//    int deleteAllByStudentId(String studentId);
//
//    /**
//     * 根据学生id查询对应的扩展信息
//     * @param studentId
//     * @param expandName
//     * @return
//     */
//    @Transactional(readOnly = true)
//    List<StudentExpand> findAllByStudentIdAndExpandName(String studentId, String expandName);
//
////    @Transactional(readOnly = true)
////    List<StudentExpand> findByIsValidatedEquals(String isValidated);
//
//    @Transactional(readOnly = true)
//    List<StudentExpandExportDto> findAllByIsValidatedEquals(String isValidated);
//}
