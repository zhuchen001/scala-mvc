package com.example.mvc.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.mvc.scala.POUtils;

public abstract class QueryWrapperUtil {
    public static <T> QueryWrapper<T> createQueryWrapper(Class<T> claz){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.setEntityClass(claz);
        return queryWrapper;
    }

    public static QueryWrapper eq(QueryWrapper queryWrapper, String fieldName, Object value){
        queryWrapper.eq(POUtils.getColumnName(queryWrapper.getEntityClass(), fieldName), value);
        return queryWrapper;
    }
}
