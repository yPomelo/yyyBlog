package com.yyy.blog.service;

import com.yyy.blog.po.User;

public interface UserService {
    User checkUser(String username, String password);
}
