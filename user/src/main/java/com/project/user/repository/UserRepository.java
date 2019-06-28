package com.project.user.repository;

import com.project.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-6-28 17:13
 * @version: 1.0
 * @description:
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {

}
