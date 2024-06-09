package org.example.system.service;

import org.example.system.domain.LoginUser;

/**
 * RegisterService 接口定义了用户注册的操作。
 *
 * @since 1.0
 * @author David
 */
public interface RegisterService {

    /**
     * 用户注册方法。
     *
     * @param user 用户信息
     * @throws Exception 当注册过程出现问题时抛出异常
     */
    public void register(LoginUser user) throws Exception;
}
