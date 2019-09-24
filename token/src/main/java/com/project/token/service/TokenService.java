package com.project.token.service;

import com.auth0.jwt.JWTVerifier;

import java.util.Map;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 2019/2/20 21:59
 * @Version: 1.0
 * @Description:
 */
public interface TokenService {
    /**
     * 用微信openId生成一个一天有效期的token
     * @param userId
     * @return
     */
    String createToken(String userId);

    String createToken(String userId, String centerAreaId, String roleCode);

    String getCenterAreaId(String token);

    String getRoleCode(String token);

    boolean isAdmin(String token);

    /**
     * 获取JWT验证
     * @param openId
     * @return
     */
    JWTVerifier verifier(String openId);

    /**
     * 根据用户请求token 信息获取请求的用户信息
     * @param token
     * @return
     */
    String getUserId(String token);

    /**
     * 查询对应学生id信息
     * @param token
     * @return
     */
    String getStudentId(String token);

    /**
     * 查找老师id
     * @param token
     * @return
     */
    String getTeacherId(String token);

    /**
     * 通过token 获取学生班级id信息
     * @param token
     * @return
     */
    String getClassId(String token);

    /**
     * 保存token 到redis
     * @param key
     * @param map
     */
    void saveRedis(String key, Map<String, Object> map);
    /**
     * 移除 redis 保存的 token 数据信息
     * @param userId
     */
    void removeToken(String userId);

    /**
     * 获取微信openId
     * @param token
     * @return
     */
    String getOpenId(String token);

    /**
     * 获取用户的 session-key
     * @param key
     * @return
     */
    String getSessionKey(String key);
}