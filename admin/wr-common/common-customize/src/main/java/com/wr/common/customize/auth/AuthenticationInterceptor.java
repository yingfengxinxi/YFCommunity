package com.wr.common.customize.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.wr.common.core.business.annotation.AuthRequired;
import com.wr.common.core.exception.ServiceException;
import com.wr.common.customize.constant.ApiConstants;
import com.wr.common.customize.model.ApiUser;
import com.wr.common.redis.service.RedisService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @Author: RainCity
 * @Date: 2021-05-24 10:39:24
 * @Desc:
 */
public class AuthenticationInterceptor implements AsyncHandlerInterceptor {

    @Autowired
    private RedisService redisService;

    private static final String INVALID_MSG ="登录失效，请重新登录";
    private static final String NOT_EXIST_MSG ="用户不存在，请重新登录";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        // 判断接口是否需要登录
        AuthRequired methodAnnotation = method.getAnnotation(AuthRequired.class);
        // 有 @AuthRequired 注解，需要认证
        if(null != methodAnnotation){
            // 执行认证
            // 从 http 请求头中取出 token
            String token = request.getHeader("token");
            if (token == null) {
                throw new ServiceException("未登录");
            }
            String loginName;
            try {
                // 获取 token 中的 用户手机号
                loginName = JWT.decode(token).getAudience().get(0);
                if(StringUtils.isEmpty(loginName)){
                    throw new ServiceException(INVALID_MSG);
                }
                ApiUser apiUser = redisService.getCacheObject(ApiConstants.LOGIN_KEY + loginName);
                if (null == apiUser) {
                    throw new ServiceException(NOT_EXIST_MSG);
                }
                // 验证 token
                JWTVerifier verifier =  JWT.require(Algorithm.HMAC256(apiUser.getPassword())).build();
                verifier.verify(token);
                return true;
            } catch (JWTVerificationException e) {
                throw new ServiceException(INVALID_MSG);
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) {

    }
}
