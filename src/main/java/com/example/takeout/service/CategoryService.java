package com.example.takeout.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.takeout.entity.Category;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

@Service
public interface CategoryService extends IService<Category> {
     void remove(Long ids);
     Page<Category> pageQuery(int page, int pageSize, LambdaQueryWrapper<Category> queryWrapper);
}
