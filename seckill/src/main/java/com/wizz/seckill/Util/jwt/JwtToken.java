package com.wizz.seckill.Util.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.wizz.seckill.Model.reqRes;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT工具类
 *
 * @author pjmike
 * @create 2018-05-19 15:21
 **/
public class JwtToken {
    /**
     * 加密所需盐
     */
    private static final String SECRET = "secret";
    /**
     * 令牌过期时间5小时
     */
    private static final Long TTLMILLS = 1000 * 60 * 60 * 5L;

    public static String createToken(long userid) throws UnsupportedEncodingException {
        //当前系统时间
        long nowMillis = System.currentTimeMillis();
        Date date = new Date(TTLMILLS + nowMillis);
        //利用hashmap设置JWT的头信息
        Map<String, Object> head = new HashMap<String, Object>(16);
        head.put("type", "jwt");
        head.put("alg", "HS256");
        return JWT.create()
                .withHeader(head)
                .withClaim("userid", userid)
                .withExpiresAt(date)
                .withNotBefore(new Date(nowMillis))
                .sign(Algorithm.HMAC256(SECRET));
    }

    /**
     * 验证token
     *
     * @param token
     * @return
     * @throws UnsupportedEncodingException
     */
    public static Map<String, Claim> verifyToken(String token) throws UnsupportedEncodingException {
        //用加密方式
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
        DecodedJWT jwt = jwtVerifier.verify(token);
        return jwt.getClaims();
    }

    /**
     * 判断token是否过期
     *
     * @param token
     * @return
     */
    public static boolean verifyTokenTime(String token) throws UnsupportedEncodingException {
        //用加密方式
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
        DecodedJWT jwt = jwtVerifier.verify(token);
        Date expiration = jwt.getExpiresAt();
        return expiration.before(new Date());
    }

    public static reqRes handleToken(String token){
        try{
            boolean expired = JwtToken.verifyTokenTime(token);
            if(expired){
                return new reqRes(1,"token 无效");
            }
        }catch (Exception e){
            return new reqRes(1,e.toString());
        }
        return new reqRes(0,"");
    }
}
