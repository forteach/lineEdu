package com.project.schoolroll.web.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-9-10 10:11
 * @version: 1.0
 * @description:
 */
@Data
public class OffLineScoreUpdateVo implements Serializable {
    private String scoreId;
    private String offLineScore;
    private String updateUser;

    public OffLineScoreUpdateVo() {
    }

    public OffLineScoreUpdateVo(String scoreId, String offLineScore, String updateUser) {
        this.scoreId = scoreId;
        this.offLineScore = offLineScore;
        this.updateUser = updateUser;
    }
}
