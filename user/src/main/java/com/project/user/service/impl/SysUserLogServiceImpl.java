package com.project.user.service.impl;

import com.project.user.domain.SysUserLog;
import com.project.user.repository.SysUserLogRepostory;
import com.project.user.service.SysUserLogService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 2019/9/21 19:04
 * @Version: 1.0
 * @Description:
 */
@Service
public class SysUserLogServiceImpl implements SysUserLogService {

    private final SysUserLogRepostory sysUserLogRepostory;

    public SysUserLogServiceImpl(SysUserLogRepostory sysUserLogRepostory){
        this.sysUserLogRepostory = sysUserLogRepostory;
    }

    @Async
    @Override
    public void addLog(SysUserLog sysUserLog) {
        sysUserLogRepostory.save(sysUserLog);
    }
}