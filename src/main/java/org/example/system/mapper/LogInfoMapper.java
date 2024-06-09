package org.example.system.mapper;

import org.apache.ibatis.annotations.*;
import org.example.system.domain.LoginUser;

/**
 * LogInfoMapper 接口定义了与数据库交互的登录操作。
 *
 * @since 1.0
 * @author David
 */
@Mapper
public interface LogInfoMapper {

    /**
     * 根据用户名和密码查询用户信息。
     *
     * @param username 用户名
     * @param password 密码
     * @return 用户信息
     */
    @Select("select * from sys_user where user_name = #{username} and password = #{password}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "userName", column = "user_name"),
            @Result(property = "nickName", column = "nick_name"),
            @Result(property = "password", column = "password"),
            @Result(property = "status", column = "status"),
            @Result(property = "email", column = "email"),
            @Result(property = "phoneNumber", column = "phonenumber"),
            @Result(property = "sex", column = "sex"),
            @Result(property = "avatar", column = "avatar"),
            @Result(property = "userType", column = "user_type"),
            @Result(property = "createBy", column = "create_by"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "updateBy", column = "update_by"),
            @Result(property = "updateTime", column = "update_time"),
            @Result(property = "delFlag", column = "del_flag")
    })
    LoginUser login(@Param("username") String username, @Param("password") String password);
}
