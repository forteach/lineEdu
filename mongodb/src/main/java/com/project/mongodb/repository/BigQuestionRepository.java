package com.project.mongodb.repository;

import com.project.mongodb.domain.BigQuestion;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-11-4 10:37
 * @version: 1.0
 * @description:
 */
public interface BigQuestionRepository extends MongoRepository<BigQuestion, String> {
}
