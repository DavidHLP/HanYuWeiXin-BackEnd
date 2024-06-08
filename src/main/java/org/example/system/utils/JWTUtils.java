package org.example.system.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.Data;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * JWTUtils 是一个用于生成和管理 JSON Web Token (JWT) 的工具类。
 * @author David
 * created on 2024-6-5
 */
@Data
public class JWTUtils {

    // 使用更安全的密钥生成方法
    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // 从配置文件中注入 expirationTime 属性
    private static final long DEFAULT_EXPIRATION_TIME = 3600000; // 默认值为 3600000 毫秒（60 分钟）

    /**
     * 构造方法，用于初始化 JWTUtils 实例。
     */
    public JWTUtils() {
        System.out.println("JWTUtils initialized");
    }

    /**
     * 创建 JWT。
     *
     * @param username 用户名
     * @return 生成的 JWT 字符串
     */
    public static String createJWT(String username) {
        return createJWT(username, DEFAULT_EXPIRATION_TIME);
    }

    public static String createJWT(String username, long expirationTime) {
        // 签名算法
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        // 当前时间
        Date now = new Date(System.currentTimeMillis());

        // 设置 JWT 声明
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", username); // 使用标准的 "sub" (subject) 声明
        claims.put("iat", now); // 使用标准的 "iat" (issued at) 声明

        // 生成 JWT ID
        String jwtId = UUID.randomUUID().toString();

        // 构建 JWT
        JwtBuilder builder = Jwts.builder()
                .setClaims(claims) // 设置声明
                .setId(jwtId) // 设置 JWT ID
                .setIssuedAt(now) // 设置签发时间
                .signWith(signatureAlgorithm, SECRET_KEY); // 设置签名算法和密钥

        // 设置过期时间
        if (expirationTime >= 0) {
            long expMillis = System.currentTimeMillis() + expirationTime;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp); // 使用标准的 "exp" (expiration) 声明
        }

        // 返回生成的 JWT 字符串
        return builder.compact();
    }

    /**
     * 验证 JWT 并返回声明。
     *
     * @param jwtToken 要验证的 JWT 字符串
     * @return 解析后的 Claims 声明
     * @throws Exception 如果 JWT 无效或过期
     */
    public static Claims validateJWT(String jwtToken) throws Exception {
        try {
            // 解析 JWT 字符串
            Jws<Claims> jwsClaims = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(jwtToken);

            // 返回解析后的 Claims
            return jwsClaims.getBody();
        } catch (JwtException e) {
            throw new Exception("Invalid JWT token", e);
        }
    }

    /**
     * 从 JWT 中获取用户名。
     *
     * @param jwtToken JWT 字符串
     * @return 用户名
     * @throws Exception 如果 JWT 无效或过期
     */
    public static String getUsernameFromJWT(String jwtToken) throws Exception {
        Claims claims = validateJWT(jwtToken);
        return claims.getSubject();
    }

    /**
     * 从 JWT 中获取过期时间。
     *
     * @param jwtToken JWT 字符串
     * @return 过期时间
     * @throws Exception 如果 JWT 无效或过期
     */
    public static Date getExpirationTimeFromJWT(String jwtToken) throws Exception {
        Claims claims = validateJWT(jwtToken);
        return claims.getExpiration();
    }

    /**
     * 从 JWT 中获取签发时间。
     *
     * @param jwtToken JWT 字符串
     * @return 签发时间
     * @throws Exception 如果 JWT 无效或过期
     */
    public static Date getIssuedAtFromJWT(String jwtToken) throws Exception {
        Claims claims = validateJWT(jwtToken);
        return claims.getIssuedAt();
    }

    /**
     * 从 JWT 中获取 JWT ID。
     *
     * @param jwtToken JWT 字符串
     * @return JWT ID
     * @throws Exception 如果 JWT 无效或过期
     */
    public static String getJWTIdFromJWT(String jwtToken) throws Exception {
        Claims claims = validateJWT(jwtToken);
        return claims.getId();
    }

    /**
     * 从 JWT 中获取声明。
     *
     * @param jwtToken JWT 字符串
     * @return 声明
     * @throws Exception 如果 JWT 无效或过期
     */
    public static Map<String, Object> getClaimsFromJWT(String jwtToken) throws Exception {
        Claims claims = validateJWT(jwtToken);
        return claims;
    }
}
