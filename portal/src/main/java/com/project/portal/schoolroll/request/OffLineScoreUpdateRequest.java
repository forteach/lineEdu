package com.project.portal.schoolroll.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-9-10 10:11
 * @version: 1.0
 * @description:
 */
@Data
public class OffLineScoreUpdateRequest implements Serializable {
    @ApiModelProperty(name = "scoreId", value = "成绩编号", dataType = "string")
    private String scoreId;

    @ApiModelProperty(name = "offLineScore", value = "线下成绩", dataType = "string")
    private String offLineScore;
}
