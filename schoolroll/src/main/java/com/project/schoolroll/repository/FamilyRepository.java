package com.project.schoolroll.repository;

import com.project.schoolroll.domain.Family;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-7-2 18:33
 * @version: 1.0
 * @description: 家庭成员
 */
public interface FamilyRepository extends JpaRepository<Family, String> {

}
