package com.itheima.service;

import com.itheima.pojo.User;

public interface UserService {
    void update(User user);
    // 根据用户名查询用户
    User findByUserName(String username);

    void register(String username, String password);

    void updateAvatar(String avatarUrl);

    void updatePwd(String newpwd);
}
