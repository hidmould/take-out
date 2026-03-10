package com.example.takeout.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.takeout.entity.Setmeal;
import com.example.takeout.mapper.SetmealMapper;
import com.example.takeout.service.SetmealService;
import org.springframework.stereotype.Service;



@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {
}
