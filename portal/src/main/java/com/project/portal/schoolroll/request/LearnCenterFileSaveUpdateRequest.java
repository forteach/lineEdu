package com.project.portal.schoolroll.request;

import com.project.schoolroll.domain.CenterFile;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.List;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-9-20 18:46
 * @version: 1.0
 * @description:
 */
@Data
public class LearnCenterFileSaveUpdateRequest implements Serializable {

    @ApiModelProperty(name = "files", value = "文件集合", dataType = "list")
    private List<CenterFile> files;

    @ApiModelProperty(name = "centerId", value = "学习中心id", dataType = "string")
    private String centerId;
}
