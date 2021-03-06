//package com.project.schoolroll.service;
//
//import com.project.schoolroll.domain.StudentExpand;
//import com.project.schoolroll.repository.dto.StudentExpandDto;
//
//import java.util.List;
//
///**
// * @author: zhangyy
// * @email: zhang10092009@hotmail.com
// * @date: 19-7-8 15:45
// * @version: 1.0
// * @description:
// */
//public interface StudentExpandService {
//    /**
//     * 查询学生的扩展信息
//     * @param studentId
//     * @return
//     */
//    public List<StudentExpandDto> findStudentExpandInfo(String studentId);
//
//    /**
//     * 删除学生的全部扩展信息
//     * @param studentId
//     * @return
//     */
//    public int deleteAllStudentExpandByStudentId(String studentId);
//
//    /**
//     * 删除对应的学生其中一个扩展信息
//     * @param expandId
//     */
//    public void deleteById(String expandId);
//
//    /**
//     * 保存修改学生的扩展信息
//     * @param studentExpandVo
//     */
//    public void saveUpdateStudentExpand(List<StudentExpand> studentExpandVo, String studentId);
//}