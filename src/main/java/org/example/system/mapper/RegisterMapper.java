package org.example.system.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.system.domain.LoginUser;

@Mapper
public interface RegisterMapper {
    public void registerUser(LoginUser user);
}
