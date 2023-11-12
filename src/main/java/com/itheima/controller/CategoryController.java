package com.itheima.controller;


import com.itheima.pojo.Category;
import com.itheima.pojo.Result;
import com.itheima.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")

public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @PostMapping("/add")
    public Result add(@RequestBody @Validated(Category.Add.class) Category category) {

        categoryService.add(category);

        return Result.success();
    }


    /**
     * 根据文章得所有分类列表
     * @return
     */
    @GetMapping
    public Result<List<Category>> list() {
        List <Category> ls = categoryService.list();
        return Result.success(ls);
    }

    /**
     * 根据id获取文章分类
     */
    @GetMapping("/detail")
    public Result<Category> detail(Integer id) {
        Category cate = categoryService.findById(id);
        return Result.success(cate);
    }

    @DeleteMapping
    public Result delete(@RequestParam Integer id) {
        categoryService.deleteById(id);

        return Result.success();
    }
    /**
     * 更新文章分类
     * @param category
     * @return
     */
    @PutMapping
    public Result update(@RequestBody @Validated(Category.Update.class) Category category) {

        categoryService.update(category);
        return Result.success();
    }
}
