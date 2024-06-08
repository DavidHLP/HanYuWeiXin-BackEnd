package org.example.system.service.Impl;

import org.example.system.domain.LoginUser;
import org.example.system.mapper.LogInfoMapper;
import org.example.system.service.LogInfoService;
import org.example.system.utils.HashUtils;
import org.example.system.utils.JWTUtils;
import org.example.system.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class LogInfoServiceImpl implements LogInfoService {
    @Resource
    private LogInfoMapper logInfoMapper;

    @Autowired
    private RedisCache redisCache;
    @Override
    public String login(String username, String password) throws Exception {
        try {
            password = init(password);
        }catch (Exception e){
            throw e;
        }
        LoginUser user = logInfoMapper.login(username, password);
        String Token = JWTUtils.createJWT(user.getUserName());
        redisCache.setCacheObject(Token, user, 1, TimeUnit.HOURS);
        LoginUser u = redisCache.getCacheObject(Token);
        System.out.println(u);
        return Token;
    }

    public String init(String password) throws Exception {
        if (password == null) throw new NullPointerException("密码不能为NULL。");
        try {
            password = HashUtils.hashWithSHA256Hex(password);
        } catch (NoSuchAlgorithmException meeseage) {
            throw new NoSuchAlgorithmException("密码加密失败。");
        } catch (NullPointerException meeseage) {
            throw new NullPointerException("密码不能为NULL。");
        } catch (Exception e) {
            throw new Exception("未知错误，不支持注册。");
        }
        return password;
    }
}
