package com.example.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @PostMapping("/login")
    public String login(@RequestParam(required = false) String username, @RequestParam(required = false) String password) {
        // 校验入参是否为空
        if (username == null || username.isEmpty()) {
            return "{\"code\": 400, \"message\": \"Username is required\"}";
        }
        if (password == null || password.isEmpty()) {
            return "{\"code\": 400, \"message\": \"Password is required\"}";
        }

        // 简单的验证逻辑（实际项目中应使用更安全的方式）
        if ("admin".equals(username) && "password".equals(password)) {
            return "{\"code\": 200, \"message\": \"Login successful!\"}";
        } else {
            return "{\"code\": 401, \"message\": \"Invalid credentials!\"}";
        }
    }
}