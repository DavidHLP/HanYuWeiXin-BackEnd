package org.example.system.service.Impl;

import org.example.system.domain.LoginUser;
import org.example.system.mapper.RegisterMapper;
import org.example.system.service.RegisterService;
import org.example.system.utils.HashUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;

@Service
public class RegisterServiceImpl implements RegisterService {
    @Resource
    private RegisterMapper registerMapper;

    @Transactional(rollbackFor = Exception.class)
    public void register(LoginUser user) throws Exception {

        try {
            user = initregister(user);
        } catch (Exception e) {
            throw e;
        }
        registerMapper.registerUser(user);
    }

    public LoginUser initregister(LoginUser user) throws Exception {
        if (user == null) throw new NullPointerException("User 不能为NULL。");
        try {
            user.setPassword(HashUtils.hashWithSHA256Hex(user.getPassword()));
        } catch (NoSuchAlgorithmException meeseage) {
            throw new NoSuchAlgorithmException("密码加密失败。");
        } catch (NullPointerException meeseage) {
            throw new NullPointerException("User 密码不能为NULL。");
        } catch (Exception e) {
            throw new Exception("未知错误，不支持注册。");
        }
        return user;
    }

}
