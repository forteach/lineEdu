package com.project.mongodb.service.impl;

import com.project.mongodb.domain.UserRecord;
import com.project.mongodb.repository.UserRecordRepository;
import com.project.mongodb.service.UserRecordService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-11-22 10:38
 * @version: 1.0
 * @description:
 */
@Service
public class UserRecordServiceImpl implements UserRecordService {
    private final UserRecordRepository userRecordRepository;

    public UserRecordServiceImpl(UserRecordRepository userRecordRepository) {
        this.userRecordRepository = userRecordRepository;
    }


    @Override
    @Async
    @Transactional(rollbackFor = Exception.class)
    public void save(UserRecord userRecord) {
        userRecordRepository.save(userRecord);
    }

    @Override
    public Page<UserRecord> findAllPageByCenterId(String centerId, PageRequest request) {
        return userRecordRepository.findAllByCenterAreaIdOrderByIdDesc(centerId, request);
    }

    @Override
    public Page<UserRecord> findAllPage(PageRequest request) {
        return userRecordRepository.findAllByOrderByIdDesc(request);
    }
}