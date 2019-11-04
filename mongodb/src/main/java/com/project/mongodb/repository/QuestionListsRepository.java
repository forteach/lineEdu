package com.project.mongodb.repository;

import com.project.mongodb.domain.QuestionsLists;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-11-4 10:02
 * @version: 1.0
 * @description:
 */
public interface QuestionListsRepository extends MongoRepository<QuestionsLists, String> {
    @Transactional(readOnly = true)
    List<QuestionsLists> findAllByCourseIdAndStudentId(String courseId, String studentId);
}
