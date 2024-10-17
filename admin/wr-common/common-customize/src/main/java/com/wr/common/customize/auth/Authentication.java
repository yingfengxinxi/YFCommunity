package com.wr.common.customize.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;

/**
 * @Author: RainCity
 * @Date: 2021-05-24 11:47:04
 * @Desc: jwt 根据当前登录人生成token
 */
public class Authentication {
    private static final String ISSUER="xccj_community";

    public static String createToken(String loginName, String password) {
        /*Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR, 24);*/
        return JWT.create()
                .withJWTId("community_jwtId")
                //签发者
                .withIssuer(ISSUER)
                //将 登录名 保存到token里面
                .withAudience(loginName)
                //签发时间
                .withIssuedAt(new Date())
                //过期时间
                //.withExpiresAt(cal.getTime())
                //以 password 作为token的密钥
                .sign(Algorithm.HMAC256(password));
    }
}
