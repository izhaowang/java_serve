package com.itheima.controller;

import com.itheima.pojo.User;
import com.itheima.service.UserService;
import com.itheima.utils.Md5Util;
import com.itheima.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.itheima.pojo.Result;
import com.itheima.utils.JwtUtil;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$")String password) {

        // 查询用户
        User u = userService.findByUserName(username);
        if (u ==null) {
            // 没有占用
            // 没有占用然后注册
            userService.register(username, password);
            return Result.success();
        } else {
            // 占用了
            return Result.error("用户名已经存在");
        }

    }

    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password) {

        //根据用户名查询用户
        User u = userService.findByUserName(username);
        // 判断用户是否存在
        if (u == null) {
            return Result.error("用户名错误");
        }
        // 判断密码是否正确
        // 输入的密码经过md5加密后是否和数据库里 用户名对应的密码一样
        if (Md5Util.getMD5String(password).equals(u.getPassword())) {
            Map<String, Object> claim = new HashMap<>();
            claim.put("id", u.getId());
            claim.put("username", username);
            String token = JwtUtil.genToken(claim); // 生产token
            // 将token存贮到redis
            ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
            ops.set(token, token, 1, TimeUnit.HOURS);
            return  Result.success(token);
        }
        return Result.error("密码错误");
    }

    @GetMapping("/userInfo")
    public Result<User> userInfo() {
        // 根据用户名查询用户，但是用户名不是从参数获取得，而是从jwt authorization字段获取
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User u = userService.findByUserName(username);
        return Result.success(u);
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Validated User user) {
        userService.update(user);
        return Result.success();
    }
    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl) {
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }

    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String, String> params, @RequestHeader("Authorization") String token) {
        // 校验参数
        String oldpwd = params.get("old_pwd");
        String newpwd = params.get("new_pwd");
        String repwd = params.get("re_pwd");

        if (!StringUtils.hasLength(oldpwd) || !StringUtils.hasText(newpwd) || !StringUtils.hasText(repwd)) {


            return  Result.error("缺少必要得参数");
        }

        // 检验原密码
        Map<String, Object> map = ThreadLocalUtil.get();
        String username =(String) map.get("username");
        User u = userService.findByUserName(username);
        if(!u.getPassword().equals(Md5Util.getMD5String(oldpwd))) {
            return  Result.error("原密码错误");
        }

        // 检验newpwd 和 repwd是否一样
        if(!newpwd.equals(repwd)) {
            return  Result.error("两次输入得密码不一致");
        }
        // 以上校验都通过了
        // 调用server层
        userService.updatePwd(newpwd);
        // 更新了密码需要删除redis里面的token
        ValueOperations<String, String> ops =stringRedisTemplate.opsForValue();
        ops.getOperations().delete("token");
        return Result.success();

    }
}

