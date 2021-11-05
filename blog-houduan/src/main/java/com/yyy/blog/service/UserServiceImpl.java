package com.yyy.blog.service;

import com.yyy.blog.Util.MD5Utils;
import com.yyy.blog.dao.UserRepository;
import com.yyy.blog.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public User checkUser(String username, String password) {
        User user = userRepository.findByUserNameAndPassword(username, MD5Utils.code(password));
        return user;
    }
}
