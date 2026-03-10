package com.example.takeout.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.takeout.common.R;
import com.example.takeout.entity.Category;
import com.example.takeout.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;

@RestController
@Slf4j
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 新增分类
     */
    @PostMapping
    public R<String> save(@RequestBody Category category,HttpServletRequest request){
        // 设置创建时间
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        Long empId = (Long) request.getSession().getAttribute("employee");
        category.setCreateUser(empId);
        category.setUpdateUser(empId);
        log.info("category:{}",category);
        categoryService.save(category);
        return R.success("新增分类成功");
    }

    /**
     * 分页查询
     */
    /**
     * 分页查询
     */
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize){
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(Category::getSort);
        Page<Category> pageInfo = categoryService.pageQuery(page, pageSize, queryWrapper);
        log.info("分页查询结果：总数={}, 记录数={}", pageInfo.getTotal(), pageInfo.getRecords().size());
        return R.success(pageInfo);
    }



    /**
     * 删除分类
     */
    /**
     * 删除分类
     */
    /**
     * 删除分类
     */
    @DeleteMapping
    public R<String> delete(@RequestParam String ids){
        Long id = Long.parseLong(ids);
        log.info("删除分类，id 为：{}", id);
        categoryService.remove(id);
        return R.success("分类信息删除成功");
    }





}
