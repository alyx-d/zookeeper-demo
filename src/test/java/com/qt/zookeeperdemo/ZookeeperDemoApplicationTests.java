package com.qt.zookeeperdemo;

import com.qt.zookeeperdemo.model.UserModel;
import com.qt.zookeeperdemo.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@SpringBootTest
class ZookeeperDemoApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void createUser() {
        var user = new UserModel();
        user.setUsername("user");
        user.setPassword(passwordEncoder.encode("123456"));
        user.setCreateTime(LocalDateTime.now());
        userService.save(user);
    }
}
