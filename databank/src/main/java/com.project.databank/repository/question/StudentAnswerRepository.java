package com.project.databank.repository.question;

import com.project.databank.domain.question.StudentAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 18-11-15 14:49
 * @Version: 1.0
 * @Description: 学生答题的答案
 */
public interface StudentAnswerRepository extends JpaRepository<StudentAnswer, String> {

}
