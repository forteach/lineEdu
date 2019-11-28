package com.project.databank.repository.question;

import com.project.databank.domain.question.QuestionTxt;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 18-11-15 14:56
 * @Version: 1.0
 * @Description: 问题册内容
 */
public interface QuestionTxtRepository extends JpaRepository<QuestionTxt, String> {
}
