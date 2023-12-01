package com.example.mvc.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mvc.domain.ProductBean;
import com.example.mvc.domain.SubBean;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface SubMapper extends BaseMapper<SubBean> {
}
