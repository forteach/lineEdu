//package com.project.schoolroll.repository;
//
//import com.project.schoolroll.domain.StudentExpandDictionary;
//import com.project.schoolroll.repository.dto.StudentExpandDictionaryDto;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
///**
// * @author: zhangyy
// * @email: zhang10092009@hotmail.com
// * @date: 19-7-30 16:10
// * @version: 1.0
// * @description:
// */
//public interface StudentExpandDictionaryRepository extends JpaRepository<StudentExpandDictionary, String> {
//    /**
//     * 查询有的字段
//     * @param isValidated
//     * @return
//     */
//    @Transactional(readOnly = true)
//    List<StudentExpandDictionaryDto> findByIsValidatedEquals(String isValidated);
//}
