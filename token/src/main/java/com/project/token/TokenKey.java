package com.project.token;

public class TokenKey {
    /**
     * 用户token 的有效时间
     */
    public final static Long TOKEN_VALIDITY_TIME = 3600L * 24 * 7;

    /**
     * 微信端学生类型
     */
    public final static String TOKEN_STUDENT ="student";
    /**
     * 登录认证的教师类型
     */
    public final static String TOKEN_TEACHER ="teacher";

    /**
     * 用户 token 前缀
     */
    public final static String USER_TOKEN_PREFIX ="userToken$";
}
