package com.example.mvc.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mvc.domain.ProductBean;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface ProductMapper extends BaseMapper<ProductBean> {
}
