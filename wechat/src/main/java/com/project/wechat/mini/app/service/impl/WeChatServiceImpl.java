package com.project.wechat.mini.app.service.impl;

import com.project.wechat.mini.app.domain.WeChatLog;
import com.project.wechat.mini.app.repository.WeChatLogRepository;
import com.project.wechat.mini.app.service.WeChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 2019/9/21 19:12
 * @Version: 1.0
 * @Description:
 */
@Service
public class WeChatServiceImpl implements WeChatService {

    private final WeChatLogRepository weChatLogRepository;

    @Autowired
    public WeChatServiceImpl(WeChatLogRepository weChatLogRepository) {
        this.weChatLogRepository = weChatLogRepository;
    }

    @Async
    @Override
    public void addLog(WeChatLog weChatLog) {
        weChatLogRepository.save(weChatLog);
    }
}
