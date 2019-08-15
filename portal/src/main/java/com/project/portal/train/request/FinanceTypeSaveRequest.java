package com.project.portal.train.request;

import com.project.portal.request.BaseReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-7-8 09:58
 * @version: 1.0
 * @description: 财务类型
 */
@Data
@ApiModel(value = "财务类型添加修改")
public class FinanceTypeSaveRequest extends BaseReq {
    /**
     * 项目id
     */
    @ApiModelProperty(name = "trainProjectId", value = "培训财务类型编号")
    private String financeTypeId;

    @ApiModelProperty(name = "trainProjectId", value = "培训财务类型名称")
    private String financeTypeName;


    @ApiModelProperty(name = "trainProjectId", value = "培训班级名称")
    private String trainClassName;


    @ApiModelProperty(name = "trainProjectId", value = "培训班级管理员")
    private String classAdmin;


    @ApiModelProperty(name = "trainProjectId", value = "培训班级管理员电话")
    private String classAdminTel;


    @ApiModelProperty(name = "trainProjectId", value = "线上线下")
    private String lineNoline;

}
