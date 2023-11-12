package com.itheima.service;

import com.itheima.pojo.Category;

import java.util.List;

public interface CategoryService {
    // 分类
    void add(Category category);

    // 列表查询
    List<Category> list();

    // 根据id查询分类信息

    Category findById(Integer id);

    void update(Category category);

    void deleteById(Integer id);
}
