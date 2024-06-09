package org.example.system.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.system.domain.LoginUser;

/**
 * RegisterMapper 接口定义了与数据库交互的注册操作。
 *
 * @since 1.0
 * @author David
 */
@Mapper
public interface RegisterMapper {

    /**
     * 注册新用户。
     *
     * @param user 用户信息
     */
    public void registerUser(LoginUser user);
}
