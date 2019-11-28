package com.project.portal.databank.request;

import com.project.databank.web.vo.DataDatumVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 18-12-3 17:09
 * @Version: 1.0
 * @Description:
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "章节资料信息对象数据")
public class ChapteDataReq {

    @ApiModelProperty(name = "courseId", value = "科目编号", dataType = "string", required = true)
    private String courseId;

    @ApiModelProperty(value = "章节编号", name = "chapterId", dataType = "string")
    private String chapterId;

    @ApiModelProperty(name = "datumType", dataType = "string", value = "资料类型", example = "1", notes = "资料类型 1文档　　3视频　4音频　5链接", required = true)
    private String datumType;

    @ApiModelProperty(value = "files", name = "文件列表信息", dataType = "list", required = true)
    private List<DataDatumVo> files;

    @ApiModelProperty(value = "创建人", name = "createUser", dataType = "string")
    private String createUser;
}
