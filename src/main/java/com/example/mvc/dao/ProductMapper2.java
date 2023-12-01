package com.example.mvc.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mvc.domain.ProductBean;
import com.example.mvc.domain.ProductBean2;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface ProductMapper2 extends BaseMapper<ProductBean2> {
}
