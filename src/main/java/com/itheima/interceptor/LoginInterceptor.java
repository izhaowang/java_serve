package com.itheima.interceptor;

import com.itheima.pojo.Result;
import com.itheima.utils.JwtUtil;
import com.itheima.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import java.util.Map;

@Component

public class LoginInterceptor  implements HandlerInterceptor {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public boolean preHandle(HttpServletRequest requst, HttpServletResponse response, Object handler) throws Exception {

        // 令牌验证
        String token = requst.getHeader("Authorization");
        // 解析token
        try {
            // 从Redis获取相同的token
            ValueOperations<String, String> ops =stringRedisTemplate.opsForValue();
            String redisToken = ops.get(token);
            if(redisToken == null) {
                // redis 没有token 已经失效了
                throw  new RuntimeException();
            }
            Map<String, Object> claims = JwtUtil.parseToken(token);
            ThreadLocalUtil.set(claims);
            System.out.println(claims);
            //
            return true; // 返回true放行了
        } catch (Exception e) {
           response.setStatus(401);
           return false; // 不放行
        }


    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    //     清空threadLocaL 得数据 防止内存泄露
        ThreadLocalUtil.remove();
    }
}
