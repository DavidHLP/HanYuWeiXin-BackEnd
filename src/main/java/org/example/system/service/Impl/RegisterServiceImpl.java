package org.example.system.service.Impl;

import org.example.system.domain.LoginUser;
import org.example.system.mapper.RegisterMapper;
import org.example.system.service.RegisterService;
import org.example.system.utils.HashUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;

/**
 * RegisterServiceImpl 是 RegisterService 接口的实现类，处理用户注册逻辑。
 *
 * @since 1.0
 * @author David
 */
@Service
public class RegisterServiceImpl implements RegisterService {
    @Resource
    private RegisterMapper registerMapper;

    /**
     * 用户注册方法，初始化用户信息并保存到数据库。
     *
     * @param user 用户信息
     * @throws Exception 当用户信息处理或保存过程出现问题时抛出异常
     */
    @Transactional(rollbackFor = Exception.class)
    public void register(LoginUser user) throws Exception {
        try {
            user = initregister(user);
        } catch (Exception e) {
            throw e;
        }
        registerMapper.registerUser(user);
    }

    /**
     * 初始化用户信息，使用 SHA-256 对密码进行加密。
     *
     * @param user 用户信息
     * @return 初始化后的用户信息
     * @throws Exception 当用户信息为空或加密过程出现问题时抛出异常
     */
    public LoginUser initregister(LoginUser user) throws Exception {
        if (user == null) throw new NullPointerException("User 不能为NULL。");
        try {
            user.setPassword(HashUtils.hashWithSHA256Hex(user.getPassword()));
        } catch (NoSuchAlgorithmException e) {
            throw new NoSuchAlgorithmException("密码加密失败。");
        } catch (NullPointerException e) {
            throw new NullPointerException("User 密码不能为NULL。");
        } catch (Exception e) {
            throw new Exception("未知错误，不支持注册。");
        }
        return user;
    }
}
