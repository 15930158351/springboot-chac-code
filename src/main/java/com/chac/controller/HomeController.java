package com.chac.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    
    @GetMapping("/")
    public String home() {
        return "Spring Boot应用启动成功！数据库连接正常。";
    }
    
    @GetMapping("/health")
    public String health() {
        return "应用运行正常";
    }
} 