package com.project.questionlibrary.domain;

import com.project.questionlibrary.domain.base.QuestionBase;
import com.project.questionlibrary.domain.base.QuestionBody;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 2020/1/6 11:59
 * @version: 1.0
 * @description:　选择题
 */
public class SelectQuestion<T extends QuestionBody> extends QuestionBase {
    private T body;
}