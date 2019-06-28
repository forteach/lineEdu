package com.project.portal.config.Interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.project.base.exception.MyAssert;
import com.project.base.common.keyword.DefineCode;
import com.project.token.annotation.PassToken;
import com.project.token.service.TokenService;
import com.project.token.annotation.UserLoginToken;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.method.HandlerMethod;

import javax.annotation.Resource;
import java.lang.reflect.Method;

import static com.project.token.constant.TokenKey.USER_TOKEN_PREFIX;

/**
 * @Description:
 * @author: liu zhenming
 * @version: V1.0
 * @date: 2018/10/30 15:27
 */
@Slf4j
@NoArgsConstructor
public class SysUserLoginInterceptor{

    @Resource
    private TokenService tokenService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public boolean TokenVal(String token,Object handler){
        // 从 http 请求头中取出 token
        // 如果不是映射到方法直接通过
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod=(HandlerMethod)handler;
        Method method =handlerMethod.getMethod();
        //检查是否有 passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }
        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(UserLoginToken.class)) {
            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
            if (userLoginToken.required()) {
                // 执行认证
                MyAssert.blank(token, DefineCode.ERR0004, "token is null");
                // 获取 token 中的 openId
                String openId = null;
                try {
                    openId = JWT.decode(token).getAudience().get(0);
                } catch (JWTDecodeException j) {
                    if (log.isErrorEnabled()){
                        log.error("token 校验非法 401");
                    }
                    MyAssert.fail(DefineCode.ERR0004, j, "401");
                }
                if (!stringRedisTemplate.hasKey(USER_TOKEN_PREFIX.concat(openId))) {
                    if (log.isErrorEnabled()){
                        log.error("token 已经过期，请重新登录");
                    }
                    MyAssert.fail(DefineCode.ERR0004, new TokenExpiredException("token 已经过期，请重新登录"), "token 已经过期，请重新登录");
                }
                // 验证 token
                try {
                    tokenService.verifier(openId).verify(token);
                } catch (JWTVerificationException e) {
                    if (log.isErrorEnabled()){
                        log.error("token 非法无效 401");
                    }
                    MyAssert.fail(DefineCode.ERR0004, e, "非法 token");
                }
                return true;
            }
        }
        return true;
    }

}
