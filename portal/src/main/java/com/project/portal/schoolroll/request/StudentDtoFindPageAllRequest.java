package com.project.portal.schoolroll.request;

import com.project.portal.request.SortVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-7-4 17:31
 * @version: 1.0
 * @description:
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "查询学生信息条件")
public class StudentDtoFindPageAllRequest extends SortVo implements Serializable {
    @ApiModelProperty(name = "stuId", value = "学生id", dataType = "string")
    private String stuId;
    @ApiModelProperty(name = "stuName", value = "学生名字", dataType = "string")
    private String stuName;
    @ApiModelProperty(name = "centerIds", value = "学习中心id集合", dataType = "string")
    private List<String> centerIds;
    @ApiModelProperty(name = "studentCategory", value = "学习类别", dataType = "string")
    private String studentCategory;
    @ApiModelProperty(name = "classId", value = "班级id", dataType = "string")
    private String classId;
    @ApiModelProperty(name = "specialtyId", value = "专业Id", dataType = "string")
    private String specialtyId;
    @ApiModelProperty(name = "specialtyNames", value = "专业名称集合", dataType = "list")
    private List<String> specialtyNames;
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
    @ApiModelProperty(name = "grades", value = "年级", dataType = "list")
    private List<String> grades;

}
