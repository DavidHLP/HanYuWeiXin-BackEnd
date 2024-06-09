package org.example.system.service.Impl;

import org.example.system.domain.LoginUser;
import org.example.system.mapper.LogInfoMapper;
import org.example.system.service.LogInfoService;
import org.example.system.utils.HashUtils;
import org.example.system.utils.JWTUtils;
import org.example.system.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

/**
 * LogInfoServiceImpl 是 LogInfoService 接口的实现类，处理用户登录逻辑。
 *
 * @since 1.0
 * @author David
 */
@Service
public class LogInfoServiceImpl implements LogInfoService {

    @Resource
    private LogInfoMapper logInfoMapper;

    @Autowired
    private RedisCache redisCache;

    /**
     * 用户登录方法，验证用户名和密码，并生成 JWT 令牌。
     *
     * @param username 用户名
     * @param password 密码
     * @return JWT 令牌
     * @throws Exception 当密码加密或其他处理出现问题时抛出异常
     */
    @Override
    public String login(String username, String password) throws Exception {
        try {
            password = init(password);
        } catch (Exception e) {
            throw e;
        }
        LoginUser user = logInfoMapper.login(username, password);
        String token = JWTUtils.createJWT(user.getUserName());
        redisCache.setCacheObject(token, user, 1, TimeUnit.HOURS);
        LoginUser u = redisCache.getCacheObject(token);
        System.out.println(u);
        return token;
    }

    /**
     * 初始化密码，使用 SHA-256 进行加密。
     *
     * @param password 密码
     * @return 加密后的密码
     * @throws Exception 当密码为空或加密过程出现问题时抛出异常
     */
    public String init(String password) throws Exception {
        if (password == null) throw new NullPointerException("密码不能为NULL。");
        try {
            password = HashUtils.hashWithSHA256Hex(password);
        } catch (NoSuchAlgorithmException e) {
            throw new NoSuchAlgorithmException("密码加密失败。");
        } catch (NullPointerException e) {
            throw new NullPointerException("密码不能为NULL。");
        } catch (Exception e) {
            throw new Exception("未知错误，不支持注册。");
        }
        return password;
    }
}
