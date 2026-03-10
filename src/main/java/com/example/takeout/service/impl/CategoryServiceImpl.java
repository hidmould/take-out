package com.example.takeout.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.takeout.common.CustomException;
import com.example.takeout.entity.Category;
import com.example.takeout.entity.Dish;
import com.example.takeout.entity.Setmeal;
import com.example.takeout.mapper.CategoryMapper;
import com.example.takeout.service.CategoryService;
import com.example.takeout.service.DishService;
import com.example.takeout.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Slf4j
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private SetmealService setmealService;
    @Autowired
    private DishService dishService;

    @Transactional
    @Override
    public void remove(Long ids) {
        log.info("=== 开始删除分类，ID: {}", ids);

        // 检查是否关联菜品
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId, ids);
        Long count1 = dishService.count(dishLambdaQueryWrapper);
        log.info("=== 关联菜品数量：{}", count1);
        if (count1 > 0){
            throw new CustomException("当前分类下关联了菜品，不能删除");
        }

        // 检查是否关联套餐
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId, ids);
        Long count2 = setmealService.count(setmealLambdaQueryWrapper);
        log.info("=== 关联套餐数量：{}", count2);
        if (count2 > 0){
            throw new CustomException("当前分类下关联了套餐，不能删除");
        }

        // 直接使用 Mapper 删除
        int rows = baseMapper.deleteById(ids);
        log.info("=== 删除结果：影响 {} 行", rows);

        if (rows == 0) {
            throw new CustomException("删除失败，分类不存在");
        }
    }


    @Override
    public Page<Category> pageQuery(int page, int pageSize, LambdaQueryWrapper<Category> queryWrapper) {
        // 先查询总记录数
        Long total = this.count(queryWrapper);

        // 手动分页查询
        long offset = (long)(page - 1) * pageSize;

        // 创建新的查询条件，只保留排序
        LambdaQueryWrapper<Category> newQueryWrapper = new LambdaQueryWrapper<>();
        newQueryWrapper.orderByAsc(Category::getSort);
        newQueryWrapper.last("LIMIT " + pageSize + " OFFSET " + offset);

        Page<Category> pageInfo = new Page<>(page, pageSize, total);
        pageInfo.setRecords(this.list(newQueryWrapper));

        log.info("分页查询 - 总数：{}, 返回记录数：{}", total, pageInfo.getRecords().size());
        return pageInfo;
    }



}
