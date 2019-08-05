package com.project.schoolroll.repository;

import com.project.schoolroll.domain.Student;
import com.project.schoolroll.repository.dto.StudentPeopleDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-6-28 17:06
 * @version: 1.0
 * @description:
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, String>, JpaSpecificationExecutor<Student> {


    @Transactional(readOnly = true)
    List<Student> findAllByIsValidatedEquals(String isValidated);

}
