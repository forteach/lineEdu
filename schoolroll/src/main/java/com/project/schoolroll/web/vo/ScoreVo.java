package com.project.schoolroll.web.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 2019/12/24 17:05
 * @version: 1.0
 * @description:
 */
@Data
public class ScoreVo implements Serializable {
    private String studentId;
    private String score;
}