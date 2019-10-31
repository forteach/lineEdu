package com.project.token.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.project.token.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.project.token.constant.TokenKey.*;


/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 2019/2/20 21:59
 * @Version: 1.0
 * @Description:
 */
@Slf4j
@Service(value = "TokenService")
public class TokenServiceImpl implements TokenService {

    @Value("${token.salt}")
    private String salt;

    @Resource
    private HashOperations<String, String, String> hashOperations;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 生成一个token
     * @param userId
     * @return
     */
    @Override
    public String createToken(String userId, String centerAreaId, String roleCode) {
        return JWT.create()
                .withAudience(userId, TOKEN_TEACHER, centerAreaId, roleCode)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_VALIDITY_TIME * 1000))
                .sign(Algorithm.HMAC256(salt.concat(userId)));
    }

    @Override
    public String createToken(String openId, String centerId) {
        return JWT.create()
                .withAudience(openId, TOKEN_STUDENT, centerId)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_VALIDITY_TIME * 1000))
                .sign(Algorithm.HMAC256(salt.concat(openId)));
    }

    @Override
    public String getCenterAreaId(String token){
        return getValue(token, 2);
    }

    @Override
    public String getRoleCode(String token) {
        if (TOKEN_TEACHER.equals(getValue(token, 1))){
            return getValue(token, 3);
        }
        return "";
    }

    @Override
    public boolean isAdmin(String token) {
        return USER_ROLE_CODE_ADMIN.equals(getRoleCode(token)) ? true : false;
    }

    @Override
    public boolean isStudent(String token) {
        return TOKEN_STUDENT.equals(getValue(token, 1)) ? true : false;
    }

    /**
     * 校验token
     * @param userId
     * @return
     */
    @Override
    public JWTVerifier verifier(String userId) {
        return JWT.require(Algorithm.HMAC256(salt.concat(userId))).build();
    }

    /**
     * 获取token携带的用户信息
     * @param token
     * @return
     */
    @Override
    public String getUserId(String token) {
        return getValue(token, 0);
    }

    @Override
    public String getStudentId(String token){
        if (TOKEN_STUDENT.equals(getValue(token, 1))){
            return hashOperations.get(getKey(getUserId(token)), "studentId");
        }
        return null;
    }

    @Override
    public String getTeacherId(String token) {
        if (TOKEN_TEACHER.equals(getValue(token, 1))){
            return getKey(getUserId(token));
        }
        return null;
    }

    @Override
    public String getClassId(String token) {
        if (TOKEN_STUDENT.equals(getValue(token, 1))){
            return hashOperations.get(getKey(getUserId(token)), "classId");
        }
        return null;
    }

    @Override
    public void saveRedis(String key, Map<String, Object> map) {
        stringRedisTemplate.opsForHash().putAll(key, map);
        //设置有效期7天
        stringRedisTemplate.expire(key, TOKEN_VALIDITY_TIME, TimeUnit.SECONDS);
    }

    @Override
    public void removeToken(String userId) {
        stringRedisTemplate.delete(this.getKey(userId));
    }

    @Override
    public String getOpenId(String token) {
        return JWT.decode(token).getAudience().get(0);
    }

    @Override
    public String getSessionKey(String key) {
        return hashOperations.get(key, "sessionKey");
    }

    private String getValue(String token, int index){
        return JWT.decode(token).getAudience().get(index);
    }

    private String getKey(String userId){
        return USER_TOKEN_PREFIX.concat(userId);
    }
}
