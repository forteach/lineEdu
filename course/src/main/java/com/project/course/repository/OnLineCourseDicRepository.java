package com.project.course.repository;

import com.project.course.domain.OnLineCourseDic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 2019/9/8 11:27
 * @Version: 1.0
 * @Description:
 */
public interface OnLineCourseDicRepository extends JpaRepository<OnLineCourseDic, String> {
    @Transactional(readOnly = true)
    List<OnLineCourseDic> findAllByIsValidatedEqualsAndCenterAreaId(String isValidated, String centerAreaId);

    @Transactional(readOnly = true)
    List<OnLineCourseDic> findAllByIsValidatedEquals(String isValidated);

    @Transactional(readOnly = true)
    Optional<OnLineCourseDic> findByCourseName(String courseName);
}