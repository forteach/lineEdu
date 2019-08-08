package com.project.portal.user.repository;


import com.project.base.common.keyword.Dic;
import com.project.user.domain.SysRole;
import com.project.user.domain.SysUsers;
import com.project.user.domain.UserRole;
import com.project.user.repository.SysRoleRepository;
import com.project.user.repository.SysUsersRepository;
import com.project.user.repository.UserRoleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-3-8 17:58
 * @version: 1.0
 * @description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRoleRepositoryTest {

    @Resource
    private UserRoleRepository userRoleRepository;
    @Resource
    private SysUsersRepository sysUsersRepository;
    @Resource
    private SysRoleRepository sysRoleRepository;
    @Test
    public void save(){
        Optional<String> sysRoleId = sysRoleRepository.findByIsValidatedEquals(Dic.TAKE_EFFECT_OPEN).stream().filter(sysRole -> "管理员".equals(sysRole.getRoleName()))
                .map(SysRole::getRoleId).findFirst();
        ArrayList<UserRole> userRoles = new ArrayList<>();
        List<SysUsers> sysUsers = sysUsersRepository.findByIsValidatedEquals(Dic.TAKE_EFFECT_OPEN);
        sysRoleId.ifPresent(s -> sysUsers.forEach(sy -> {
            userRoles.add(
                    UserRole.builder()
                    .userId(sy.getId())
                    .roleId(s)
                    .build());
        }));
        userRoleRepository.saveAll(userRoles);
    }
}