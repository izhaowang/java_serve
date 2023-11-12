package com.itheima.pojo;



import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;

// lombok 编译阶段，为实体类自动生产setter getter
// 首先再pom中添加依赖
// 再实体类添加注解 @Data
@Data
public class User {
    @NotNull
    private Integer id;//主键ID
    private String username;//用户名
    @JsonIgnore // springmvc 把对象转成成json得字符串得时候，，忽略password。最重返回得json没有password
    private String password;//密码
    @NotEmpty
    @Pattern(regexp = "^\\S{5,16}$")
    private String nickname;//昵称
    @NotEmpty
    @Email
    private String email;//邮箱
    private String userPic;//用户头像地址
    private LocalDateTime createTime;//创建时间
    private LocalDateTime updateTime;//更新时间
}
