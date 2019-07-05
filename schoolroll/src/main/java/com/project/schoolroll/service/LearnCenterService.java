package com.project.schoolroll.service;

import com.project.schoolroll.dto.LearnCenterDto;

import java.util.List;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-7-4 17:38
 * @version: 1.0
 * @description:
 */
public interface LearnCenterService {
    /**
     * 查询全部有效学习中心
     * @return
     */
    public List<LearnCenterDto> findAll();
}
