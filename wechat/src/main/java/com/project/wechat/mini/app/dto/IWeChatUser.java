package com.project.wechat.mini.app.dto;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-5-17 15:59
 * @version: 1.0
 * @description:
 */
public interface IWeChatUser {

    /** 学生角色是 教师角色是手机号码身份证号码*/
    public String getStudentId();

    public String getClassId();

    public String getClassName();

    public String getStudentName();

    public String getPortrait();

    /** 学习中心*/
    public String getCenterAreaId();

    /** 角色类型角色 0 教师 1 学生*/
    public String getRoleId();
}