package com.itheima.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.groups.Default;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Data // 自动生成 setter getter
public class Category {

    @NotNull(groups = Update.class)
    private Integer id;//主键ID
    @NotEmpty
    private String categoryName;//分类名称
    @NotEmpty
    private String categoryAlias;//分类别名
    private Integer createUser;//创建人ID
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") // 规定从数据库返回得时间格式
    private LocalDateTime createTime;//创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") // 规定从数据库返回得时间格式
    private LocalDateTime updateTime;//更新时间


    // Default 如果没有指定groups 那么就所有得规则就属于Default 默认分组
    public interface Add extends Default {

    }

    public interface Update extends Default{

    }
}

