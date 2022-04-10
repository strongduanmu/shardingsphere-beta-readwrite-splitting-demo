package com.strongduanmu.service.impl;

import java.util.List;

import com.strongduanmu.domain.User;
import com.strongduanmu.mapper.intlbiz.UserMapper;
import com.strongduanmu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserMapper userMapper;
    
    @Override
    public List<User> selectAll() {
        return userMapper.selectAll();
    }
}
