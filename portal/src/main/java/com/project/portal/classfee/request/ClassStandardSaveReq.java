package com.project.portal.classfee.request;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 18-11-29 16:21
 * @Version: 1.0
 * @Description: 课程添加Request对象
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "课时费标准添加、保存")
public class ClassStandardSaveReq implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "课时费编号", name = "standardId", dataType = "string")
    private String standardId;

    @ApiModelProperty(value = "课时费年份", name = "createYear", dataType = "string")
    private String createYear;

    @ApiModelProperty(value = "学习中心专业", name = "specialtyIds", dataType = "string")
    private String specialtyIds;

    @ApiModelProperty(value = "所属的教学中心编号", name = "centerAreaId", dataType = "string")
    public String centerAreaId;

    @ApiModelProperty(value = "教学中心学生数量", name = "studentSum", dataType = "int")
    private int studentSum;

    @ApiModelProperty(value = "每位学生补贴金额", name = "studentSubsidies", dataType = "int")
    private int studentSubsidies;

    @ApiModelProperty(value = "中心补贴总金额", name = "subsidiesSum", dataType = "int")
    private int subsidiesSum;

    @ApiModelProperty(value = "每节课课时费", name = "classFee", dataType = "int")
    private int classFee;
}
