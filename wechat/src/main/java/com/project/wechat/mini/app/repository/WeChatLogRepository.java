package com.project.wechat.mini.app.repository;

import com.project.wechat.mini.app.domain.WeChatLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 2019/9/21 18:50
 * @Version: 1.0
 * @Description:
 */
public interface WeChatLogRepository extends JpaRepository<WeChatLog, String> {

}