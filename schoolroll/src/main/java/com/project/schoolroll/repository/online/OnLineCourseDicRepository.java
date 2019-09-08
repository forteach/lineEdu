package com.project.schoolroll.repository.online;

import com.project.schoolroll.domain.online.OnLineCourseDic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 2019/9/8 11:27
 * @Version: 1.0
 * @Description:
 */
public interface OnLineCourseDicRepository extends JpaRepository<OnLineCourseDic, String> {

    List<OnLineCourseDic> findAllByIsValidatedEqualsAndCenterAreaId(String isValidated, String centerAreaId);

    List<OnLineCourseDic> findAllByIsValidatedEquals(String isValidated);
}