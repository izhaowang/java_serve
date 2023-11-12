package com.itheima.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Result<T> {
    private Integer code; // 业务代码 0-成功 1-失败
    private String message; // message
    private T data; //响应数据

    // 快速返回操作成功响应结果（带响应数据）
    // 带数据的成功响应方法
    public static <E> Result <E> success(E data) {
        return new Result<>(0, "操作成功", data);
    }

    //快速返回操作成功响结果
    // 不带数据的成功响应方法
    public static Result success() {
        return new Result(0, "操作成功", null);
    }
    public static Result error(String message) {
        return new Result(1, message, null);
    }
}
