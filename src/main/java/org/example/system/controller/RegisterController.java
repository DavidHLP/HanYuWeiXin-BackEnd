package org.example.system.controller;

import org.example.system.domain.LoginUser;
import org.example.system.service.RegisterService;
import org.example.system.utils.ResultMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * RegisterController 处理用户注册请求。
 *
 * @author David
 * @since 1.0
 */
@RestController
@RequestMapping("/api")
public class RegisterController {

    @Resource
    private RegisterService registerServiceImpl;

    /**
     * 用户注册接口
     *
     * @param user 用户信息
     * @return 包含结果的 ResultMap
     */
    @PostMapping("/register")
    public ResultMap register(@RequestBody(required = false) LoginUser user) {
        try {
            registerServiceImpl.register(user);
            return ResultMap.success("注册成功", null);
        } catch (Exception e) {
            return ResultMap.fail(e.toString());
        }
    }
}
