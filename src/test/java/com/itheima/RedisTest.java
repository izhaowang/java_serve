package com.itheima;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.sql.SQLOutput;
import java.util.concurrent.TimeUnit;

@SpringBootTest // 单元测试初始化之前会先初始化spring容器
public class RedisTest {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Test
    public void tesetSet() {
        // 存数据
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        valueOperations.set("username", "zwzm");
        valueOperations.set("ID", "1", 15, TimeUnit.SECONDS);

    }
    @Test
    public  void testGet() {
        // 获取数据
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        String val = valueOperations.get("username");
        System.out.println(val);
    }
}
