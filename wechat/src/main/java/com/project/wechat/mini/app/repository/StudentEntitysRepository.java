package com.project.wechat.mini.app.repository;

import com.project.wechat.mini.app.domain.StudentEntitys;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-2-14 10:59
 * @version: 1.0
 * @description:
 */
@Repository
public interface StudentEntitysRepository extends JpaRepository<StudentEntitys, String> {

    /**
     * 通过身份证号码查询有效状态的学生信息
     *
     * @param isValidated 有效状态
     * @param stuIDCard   身份证号码
     * @return
     */
    List<StudentEntitys> findByIsValidatedEqualsAndStuIDCard(String isValidated, String stuIDCard);
}
