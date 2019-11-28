package com.project.mongodb.service;

import com.project.mongodb.domain.UserRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-11-22 10:37
 * @version: 1.0
 * @description:
 */
public interface UserRecordService {
    void save(UserRecord userRecord);
    Page<UserRecord> findAllPageByCenterId(String centerId, PageRequest request);
    Page<UserRecord> findAllPage(PageRequest request);
}
