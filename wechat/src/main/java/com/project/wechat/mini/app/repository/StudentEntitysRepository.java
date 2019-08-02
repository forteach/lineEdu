package com.project.wechat.mini.app.repository;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-2-14 10:59
 * @version: 1.0
 * @description: 通过身份证号码查询有效状态的学生信息
 * @param isValidated 有效状态
 * @param stuIDCard   身份证号码
 * @return
 */
//@Repository
//public interface StudentEntitysRepository extends JpaRepository<StudentEntitys, String> {

/**
 * 通过身份证号码查询有效状态的学生信息
 *
 * @param isValidated 有效状态
 * @param stuIDCard   身份证号码
 * @return
 */
//    @Query(value = "select " +
//            " stuId as stuId , stuName as stuName, stuIDCard as stuIDCard from Student as s " +
//            " left join StudentPeople as sp on sp.")
//    @Transactional(readOnly = true)
//    List<StudentEntitys> findByIsValidatedEqualsAndStuIDCard(String isValidated, String stuIDCard);
//}