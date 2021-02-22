package com.smart.home.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.smart.home.common.contants.SecurityConsts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

/**
 * @author jason
 * @date 2020/11/3
 **/
public class JwtUtil {

    private static Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    public static String createToken(String account, String currentTimeMillis) {
        // 帐号加JWT私钥加密
        String secret = account + JwtProperties.getSecretKey();
        Algorithm algorithm = null;
        try {
            algorithm = Algorithm.HMAC256(secret);
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
        }
        return JWT.create()
                .withClaim(SecurityConsts.ACCOUNT, account)
                .withClaim(SecurityConsts.CURRENT_TIME_MILLIS, currentTimeMillis)
                .sign(algorithm);
    }

    /**
     * 校验token是否正确
     *
     * @param token
     * @return
     * @throws UnsupportedEncodingException
     * @throws TokenExpiredException
     */
    public static boolean verify(String token) {
        String secret = getClaim(token, SecurityConsts.ACCOUNT) + JwtProperties.getSecretKey();
        Algorithm algorithm = null;
        try {
            algorithm = Algorithm.HMAC256(secret);
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
            return false;
        }
        JWTVerifier verifier = JWT.require(algorithm).build();
        verifier.verify(token);
        return true;
    }

    /**
     * 获得Token中的信息无需secret解密也能获得
     * @param token
     * @param claim
     * @return
     */
    public static String getClaim(String token, String claim) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(claim).asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    public static String getAccount(String token) {
        return getClaim(token, SecurityConsts.ACCOUNT);
    }

    public static class JwtProperties {
        /**
         * token加密密钥
         */
        private static String secretKey = "secret";

        public static String getSecretKey() {
            return secretKey;
        }

        public static void setSecretKey(String secretKey) {
            JwtProperties.secretKey = secretKey;
        }
    }

}
