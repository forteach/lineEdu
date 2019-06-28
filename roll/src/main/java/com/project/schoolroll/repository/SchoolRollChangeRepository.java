package com.project.schoolroll.repository;

import com.project.schoolroll.domain.SchoolRollChange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-6-28 17:08
 * @version: 1.0
 * @description:
 */
@Repository
public interface SchoolRollChangeRepository extends JpaRepository<SchoolRollChange, String> {

}
