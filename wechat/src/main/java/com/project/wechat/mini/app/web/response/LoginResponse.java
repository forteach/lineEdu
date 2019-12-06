package com.project.wechat.mini.app.web.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 19-1-8 15:28
 * @Version: 1.0
 * @Description:
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse implements Serializable {
    /**
     * token
     */
    private String token;

    /**
     * 是否绑定　0 绑定 1 未绑定
     */
    private String binding;

    /**
     * 班级id
     */
    private String classId;

    /**
     * 班级名称
     */
    private String className;

    /**
     * 学生id
     */
    private String studentId;

    /**
     * 学生名称
     */
    private String studentName;

    /**
     * 学生头像
     */
    private String portrait;

    private String centerAreaId;
    /**
     * 微信登陆角色
     * student 1
     * teacher 0
     */
    private String roleId;

    /** 学号信息*/
    private String stuId;

    /** 专业简称*/
    private String specialtyName;

    /** 年级 入学年的 也是学生级别 如： 2019级*/
    private String grade;

    /**
     * 学制
     * 三年制，四年制，五年制，一年制
     */
    private String educationalSystem;
    /** 学习形式全日制非全日制*/
    private String learningModality;
    /** 民族*/
    private String nation;
    /**
     * 入学时间(年/月)
     */
    private String enrollmentDate;
    /** 学生电话*/
    private String stuPhone;
    /** 学生性别*/
    private String gender;
}