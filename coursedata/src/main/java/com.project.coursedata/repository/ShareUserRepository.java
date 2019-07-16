package com.project.coursedata.repository;

import com.project.coursedata.domain.ShareUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-7-16 15:57
 * @version: 1.0
 * @description:
 */
public interface ShareUserRepository extends JpaRepository<ShareUser, String> {

}
