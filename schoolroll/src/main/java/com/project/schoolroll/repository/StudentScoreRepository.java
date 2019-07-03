package com.project.schoolroll.repository;

import com.project.schoolroll.domain.StudentScore;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-7-2 18:29
 * @version: 1.0
 * @description:
 */
public interface StudentScoreRepository extends JpaRepository<StudentScore, String> {

}
