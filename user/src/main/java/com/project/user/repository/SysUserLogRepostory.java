package com.project.user.repository;

import com.project.user.domain.SysUserLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 2019/9/21 18:51
 * @Version: 1.0
 * @Description:
 */
public interface SysUserLogRepostory extends JpaRepository<SysUserLog, String> {

}