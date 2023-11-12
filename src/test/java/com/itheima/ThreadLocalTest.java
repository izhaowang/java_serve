package com.itheima;

import org.junit.jupiter.api.Test;

public class ThreadLocalTest {
    @Test
    public void testThreadLocalSetAndGET() {
        // 1. 提供ThreadLocal对象
        ThreadLocal tl = new ThreadLocal();

        // 开启线程
        new Thread(() -> {
            tl.set("晓燕");
            System.out.println(Thread.currentThread().getName() + ":" + tl.get());
            System.out.println(Thread.currentThread().getName() + ":" + tl.get());
            System.out.println(Thread.currentThread().getName() + ":" + tl.get());
        }, "蓝色").start();

        new Thread(() -> {
            tl.set("药尘");
            System.out.println(Thread.currentThread().getName() + ":" + tl.get());
            System.out.println(Thread.currentThread().getName() + ":" + tl.get());
            System.out.println(Thread.currentThread().getName() + ":" + tl.get());
        }, "绿色").start();

    }
}
