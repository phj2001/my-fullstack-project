package com.example.my_fullstack_project;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class HelloController {

    /**
     * 这个注解 @GetMapping("/api/hello") 的作用是：
     * 将 HTTP GET 请求映射到这个方法上。
     * 路径是 /api/hello。当浏览器或前端访问 http://localhost:8080/api/hello 时，
     * Spring Boot 就会调用这个 hello() 方法。
     */
    @GetMapping("/api/hello")
    public String hello() {
        return "你好，我是从后端发来的消息！";
    }
}