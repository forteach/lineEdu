package com.project.mongodb.repository;

import com.project.mongodb.domain.UserRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-11-22 10:18
 * @version: 1.0
 * @description:
 */
public interface UserRecordRepository extends MongoRepository<UserRecord, String> {

    @Transactional(readOnly = true)
    Page<UserRecord> findAllByOrderByIdDesc(PageRequest pageRequest);
    @Transactional(readOnly = true)
    Page<UserRecord> findAllByCenterAreaIdOrderByIdDesc(String centerAreaId, PageRequest pageRequest);
}