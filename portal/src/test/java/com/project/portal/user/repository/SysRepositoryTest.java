package com.project.portal.user.repository;

import cn.hutool.core.util.IdUtil;
import com.project.base.util.Md5Util;
import com.project.user.domain.SysUsers;
import com.project.user.repository.SysUsersRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-8-8 12:08
 * @version: 1.0
 * @description:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class SysRepositoryTest {
    @Value("${token.salt}")
    private String password;
    @Value("${initialization.password}")
    private String salt;
    @Autowired
    private SysUsersRepository sysUsersRepository;
    @Test
    public void saveSysUser(){
        SysUsers sysUsers = new SysUsers();
        sysUsers.setEmail("test@mail.com");
        sysUsers.setUserName("admin");
        sysUsers.setRegisterPhone("18899990000");
        sysUsers.setPassWord(Md5Util.macMD5(password.concat(salt)));
        sysUsers.setId(IdUtil.fastSimpleUUID());
        sysUsersRepository.save(sysUsers);
    }
}
