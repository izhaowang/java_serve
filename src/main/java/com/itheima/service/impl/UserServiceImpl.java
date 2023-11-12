package com.itheima.service.impl;

import com.itheima.interceptor.LoginInterceptor;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import com.itheima.utils.Md5Util;
import com.itheima.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.itheima.mapper.UserMapper;

import java.time.LocalDateTime;
import java.util.Map;

@Service

public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public User findByUserName(String username) {
        User u = userMapper.findByUserName(username);
        return u;
    }

    @Override
    public void register(String username, String password) {
        // 这个密码要先加密，因为密码明文保持到数据库中
        // md5加密
        String md5String = Md5Util.getMD5String(password);

        // 添加到
        userMapper.add(username, md5String);
    }

    @Override
    public void updateAvatar(String avatarUrl) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        userMapper.updateAvatar( avatarUrl,  id);
    }

    @Override
    public void updatePwd(String newpwd) {
        String md5String = Md5Util.getMD5String(newpwd);
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        userMapper.updatePwd(md5String, id);
    }

    @Override
    public void update(User user) {
        user.setUpdateTime(LocalDateTime.now());
        userMapper.update(user);
    }
}
