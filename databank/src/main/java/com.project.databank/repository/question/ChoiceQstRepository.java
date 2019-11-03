package com.project.databank.repository.question;

import com.project.databank.domain.question.ChoiceQst;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 18-11-15 16:36
 * @Version: 1.0
 * @Description: 选择题
 */
public interface ChoiceQstRepository extends JpaRepository<ChoiceQst, String> {

}
