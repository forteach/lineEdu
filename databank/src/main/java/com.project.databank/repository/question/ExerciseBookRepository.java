package com.project.databank.repository.question;

import com.project.databank.domain.question.ExerciseBook;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 18-11-15 15:49
 * @Version: 1.0
 * @Description:　练习册
 */
public interface ExerciseBookRepository extends JpaRepository<ExerciseBook, String> {

}
