/**
 * Copyright (C), 2015-2021, 开度
 * FileName: TokenProvider
 * Author:   ASUS
 * Date:     2021/4/19 17:44
 * Description: token
 * History:
 * <author>          <time>          <version>          <desc>
 * Hezeyu           2021/4/19           1.0              token
 */
package com.example.spring.security.spring.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 〈token〉
 *
 * @author ASUS
 * @create 2021/4/19
 * @since 1.0.0
 */
public class TokenProvider {

    private final String secretKey;
    private final int tokenValidity;

    //设置过期时间
    private static final long EXPIRE_DATE=30*60*100000;
    //token秘钥
    private static final String TOKEN_SECRET = "ZCEQIUBFKSJBFJH2020BQWE";

    public TokenProvider(String secretKey, int tokenValidity) {
        this.secretKey = secretKey;
        this.tokenValidity = tokenValidity;
    }
    public static String token (String username,String password){

        String token = "";
        try {
            //过期时间
            Date date = new Date(System.currentTimeMillis()+EXPIRE_DATE);
            //秘钥及加密算法
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            //设置头部信息
            Map<String,Object> header = new HashMap<>();
            header.put("typ","JWT");
            header.put("alg","HS256");
            //携带username，password信息，生成签名
            token = JWT.create()
                    .withHeader(header)
                    .withClaim("username",username)
                    .withClaim("password",password).withExpiresAt(date)
                    .sign(algorithm);
        }catch (Exception e){
            e.printStackTrace();
            return  null;
        }
        return token;
    }

    /**
     * @desc   验证token，通过返回true
     * @create 2019/1/18/018 9:39
     * @params [token]需要校验的串
     **/
    public static boolean verify(String token, UserDetails details){
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            Map<String, Claim> claims = jwt.getClaims();
            if(claims.get("username").asString().equals(details.getUsername())){
                return true;
            }else {
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            return  false;
        }
    }

    public static String  getUserName(String token){
        try {

            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            if(jwt.getClaims()==null){
               return null;
            }
            Map<String, Claim> claims = jwt.getClaims();
            Claim username = claims.get("username");
            return username.asString();
        }catch (Exception e){
            e.printStackTrace();
            return  null;
        }
    }

}
