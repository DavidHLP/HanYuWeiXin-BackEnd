package org.example.system.controller;

import org.example.system.service.LogInfoService;
import org.example.system.utils.ResultMap;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * LogInfoController 处理用户登录请求。
 *
 * @author David
 * @since 1.0
 */
@RestController
@RequestMapping("/api")
public class LogInfoController {

    @Resource
    private LogInfoService logInfoServiceImpl;

    /**
     * 用户登录接口
     *
     * @param username 用户名
     * @param password 密码
     * @return 包含结果的 ResponseEntity
     */
    @GetMapping("/login")
    public ResponseEntity<ResultMap> login(String username, String password) {
        try {
            String token = logInfoServiceImpl.login(username, password);

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", token);

            return new ResponseEntity<>(ResultMap.success("登录成功"), headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(ResultMap.fail(e.toString()), HttpStatus.UNAUTHORIZED);
        }
    }
}
