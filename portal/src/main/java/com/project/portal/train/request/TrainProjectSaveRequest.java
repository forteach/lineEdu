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
 * @description:
 */
@Data
@ApiModel(value = "培训项目添加修改")
public class TrainProjectSaveRequest extends BaseReq {
    /**
     * 项目id
     */
    @ApiModelProperty(name = "trainProjectId", value = "项目id")
    private String trainProjectId;
    /**
     * 项目名称
     */
    @ApiModelProperty(name = "trainProjectName", value = "项目名称")
    private String trainProjectName;
    /**
     * 项目领域
     */
    @ApiModelProperty(name = "trainAreaId", value = "项目领域")
    private String trainAreaId;

}
