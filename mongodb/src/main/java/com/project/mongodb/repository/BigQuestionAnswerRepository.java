package com.project.mongodb.repository;

import com.project.mongodb.domain.BigQuestionAnswer;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-11-20 10:36
 * @version: 1.0
 * @description:
 */
public interface BigQuestionAnswerRepository extends MongoRepository<BigQuestionAnswer, String> {
    void deleteAllByStudentId(String studentId);
}
