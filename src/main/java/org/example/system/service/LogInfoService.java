package org.example.system.service;

import org.example.system.domain.LoginUser;
import org.example.system.domain.VO.LoginInfo;

/**
 * LogInfoService 接口定义了用户登录的操作。
 *
 * @since 1.0
 * @author David
 */
public interface LogInfoService {

    /**
     * 用户登录方法。
     *
     * @param username 用户名
     * @param password 密码
     * @return JWT 令牌
     * @throws Exception 当登录过程出现问题时抛出异常
     */
    public LoginInfo login(String username, String password) throws Exception;

    /**
     * 初始化密码，使用 SHA-256 进行加密。
     *
     * @param password 密码
     * @return 加密后的密码
     * @throws Exception 当密码为空或加密过程出现问题时抛出异常
     */
    public String init(String password) throws Exception;
}
