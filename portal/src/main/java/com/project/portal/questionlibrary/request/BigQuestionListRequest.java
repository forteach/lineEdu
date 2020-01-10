package com.project.portal.questionlibrary.request;

import com.project.mongodb.domain.BigQuestion;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class BigQuestionListRequest implements Serializable {
    @ApiModelProperty(name = "bigQuestions", dataType = "list", required = true, value = "数组习题对象集")
    private List<BigQuestion> bigQuestions;
}