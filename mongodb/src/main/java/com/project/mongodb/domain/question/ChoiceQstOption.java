package com.project.mongodb.domain.question;


import lombok.Data;

/**
 * @Description: 选择题选项 ChoiceQst的子项
 * @author: liu zhenming
 * @version: V1.0
 * @date: 2018/11/15  10:10
 */
@Data
public class ChoiceQstOption {
    /**
     * 选择项题干
     */
    private String optTxt;

    /**
     * 选择项 A,B,C等Value
     */
    private String optValue;

    public ChoiceQstOption(String optTxt, String optValue) {
        this.optTxt = optTxt;
        this.optValue = optValue;
    }
}
