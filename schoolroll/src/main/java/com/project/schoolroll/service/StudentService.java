//package com.project.schoolroll.service;
//
//import com.project.schoolroll.domain.Student;
//import com.project.schoolroll.domain.StudentPeople;
//import com.project.schoolroll.repository.dto.StudentPeopleDto;
//import com.project.schoolroll.web.vo.FindStudentDtoPageAllVo;
//import com.project.schoolroll.web.vo.StudentVo;
//import org.springframework.data.domain.Page;
//
///**
// * @author: zhangyy
// * @email: zhang10092009@hotmail.com
// * @date: 19-7-1 15:10
// * @version: 1.0
// * @description:
// */
//public interface StudentService {
//
//    public Page<StudentVo> findStudentsPageAll(FindStudentDtoPageAllVo vo);
//
//    public void saveOrUpdate(Student student, StudentPeople studentPeople);
//
//    public void deleteById(String studentId);
//
//    public StudentPeopleDto findStudentPeopleDtoByStudentId(String studentId);
//}
