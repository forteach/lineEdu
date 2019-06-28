package com.project.schoolroll.repository;

import com.project.schoolroll.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-6-28 17:06
 * @version: 1.0
 * @description:
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, String> {

}
