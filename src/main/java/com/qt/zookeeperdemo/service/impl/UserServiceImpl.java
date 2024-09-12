package com.qt.zookeeperdemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qt.zookeeperdemo.mapper.UserMapper;
import com.qt.zookeeperdemo.model.UserModel;
import com.qt.zookeeperdemo.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserModel> implements UserService, UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (!StringUtils.hasText(username)) {
            return null;
        }
        var result = this.lambdaQuery()
                .select(UserModel::getUsername)
                .select(UserModel::getPassword)
                .eq(UserModel::getUsername, username)
                .oneOpt();
        if (result.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        return User.withUsername(username)
                .password(result.get().getPassword())
                .build();
    }
}
