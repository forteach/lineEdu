package com.project.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.user.domain.SysUsers;
import com.project.user.domain.UserRole;
import com.project.user.repository.UserRepository;
import com.project.user.repository.UserRoleRepository;
import com.project.user.service.UserMgrService;
import com.project.user.web.req.SysUserEditReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @Description:
 * @author: liu zhenming
 * @version: V1.0
 * @date: 2018/11/1 2:40
 */
@Slf4j
@Service
public class UserMgrServiceImpl implements UserMgrService {

    @Resource
    private UserRoleRepository userRoleRepository;

    @Resource
    private UserRepository userRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUserRole(String roleId, List<String> userIds) {
        userRoleRepository.deleteByUserIdIn(userIds);
        //存入新的用户角色信息
        userIds.forEach(userId -> {
            userRoleRepository.save(UserRole.builder()
                    .roleId(roleId)
                    .userId(userId)
                    .build());
        });
    }

    /**
     * 修改保存用户
     *
     * @param user
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysUsers edit(SysUserEditReq user) {
        Optional<SysUsers> usersOptional = userRepository.findById(user.getId());
        if (usersOptional.isPresent()) {
            SysUsers users = usersOptional.get();
            BeanUtil.copyProperties(user, users);
            return userRepository.save(users);
        }
        MyAssert.isNull(null, DefineCode.ERR0010, "要修改的用户不存在");
        return null;
    }


}
