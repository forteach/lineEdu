package com.project.portal.schoolroll.request;

import com.project.portal.request.SortVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-7-4 17:31
 * @version: 1.0
 * @description:
 */
@Data
@ApiModel(value = "查询学生信息条件")
public class StudentDtoFindPageAllRequest implements Serializable {
    @ApiModelProperty(name = "stuId", value = "学生id", dataType = "string")
    private String stuId;
    @ApiModelProperty(name = "stuName", value = "学生名字", dataType = "string")
    private String stuName;
    @ApiModelProperty(name = "centerId", value = "学习中心id", dataType = "string")
    private String centerId;
    @ApiModelProperty(name = "studentCategory", value = "学习类别", dataType = "string")
    private String studentCategory;
    @ApiModelProperty(name = "classId", value = "班级id", dataType = "string")
    private String classId;
    @ApiModelProperty(name = "specialtyId", value = "专业Id", dataType = "string")
    private String specialtyId;
    @ApiModelProperty(name = "educationalSystem", value = "学制", dataType = "string")
    private String educationalSystem;
    @ApiModelProperty(name = "waysStudy", value = "就读方式/学习方式", dataType = "string")
    private String waysStudy;
    @ApiModelProperty(name = "learningModality", value = "学习形式", dataType = "string")
    private String learningModality;
    @ApiModelProperty(name = "waysEnrollment", value = "入学方式", dataType = "string")
    private String waysEnrollment;
    @ApiModelProperty(name = "enrollmentDateStartDate", value = "开始入学时间", dataType = "string")
    private String enrollmentDateStartDate;
    @ApiModelProperty(name = "enrollmentDateEndDate", value = "结束入学时间", dataType = "string")
    private String enrollmentDateEndDate;
    @ApiModelProperty(name = "grade", value = "年级", dataType = "string")
    private String grade;

    @ApiModelProperty(value = "分页排序字段", name = "sortVo")
    private SortVo sortVo = new SortVo();
}
