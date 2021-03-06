package com.project.portal.teachplan.request;

import com.project.portal.teachplan.request.base.BaseTeachPlanSaveUpdateRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 2019/8/16 00:19
 * @Version: 1.0
 * @Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "教学计划资料信息保存")
public class TeachPlanFileSaveRequest extends BaseTeachPlanSaveUpdateRequest {

    @ApiModelProperty(name = "fileName", value = "资料名称", dataType = "string")
    private String fileName;

    @ApiModelProperty(name = "fileUrl", value = "资料URL", dataType = "string")
    private String fileUrl;

    @ApiModelProperty(name = "fileType", value = "资料类型", dataType = "string")
    private String fileType;

    @ApiModelProperty(name = "type", value = "资料类型，上传分类 A.教学大纲、B.教学计划、C.课程表 D, 教材库", dataType = "string")
    private String type;

    /**
     * 项目id
     */
//    @ApiModelProperty(name = "planId", value = "项目计划id", dataType = "string")
//    private String planId;
}