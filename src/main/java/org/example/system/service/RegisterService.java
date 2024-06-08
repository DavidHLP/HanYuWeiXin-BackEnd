package org.example.system.service;

import org.example.system.domain.LoginUser;

import java.security.NoSuchAlgorithmException;

public interface RegisterService {
    public void register(LoginUser user) throws Exception;
}
