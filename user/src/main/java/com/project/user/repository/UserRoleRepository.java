package com.project.user.repository;

import com.project.user.domain.UserRole;
import com.project.user.domain.UserRoleFundPrimarykey;
import com.project.user.repository.dto.SysRoleDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


import javax.transaction.Transactional;
import java.util.List;

/**
 * @Description: 用户角色
 * @author: liu zhenming
 * @version: V1.0
 * @date: 2018/10/31 10:00
 */
public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleFundPrimarykey> {

    /**
     * 删除权限角色
     *
     * @param roleId
     */
    @Modifying(clearAutomatically = true)
    @Transactional(rollbackOn = Exception.class)
    void deleteByRoleId(String roleId);

    /**
     * 删除权限角色
     *
     * @param userIds
     */
    @Modifying(clearAutomatically = true)
    @Transactional(rollbackOn = Exception.class)
    void deleteByUserIdIn(List<String> userIds);

    /**
     * 获得用户对应的角色
     * @param userId
     * @return
     */
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    UserRole findByUserIdIs(String userId);

    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    @Query(value = "select roleId as roleId, roleName as roleName, remark as remark, roleActivity as roleActivity " +
            " from SysRole where isValidated = '0' and roleId in " +
            " (select roleId from UserRole where isValidated = '0' and userId = ?1)")
    List<SysRoleDto> findByIsValidatedEqualsAndUserId(String userId);
}
