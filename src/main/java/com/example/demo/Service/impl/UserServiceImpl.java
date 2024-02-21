package com.example.demo.Service.impl;

import com.example.demo.Bean.User;
import com.example.demo.Dao.UserMapper;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public User getUserByName(String userName) {
        return userMapper.getUserByName(userName);
    }
}
