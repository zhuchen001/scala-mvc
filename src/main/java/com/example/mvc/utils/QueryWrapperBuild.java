package com.example.mvc.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.mvc.scala.POUtils;
import lombok.Data;

@Data
public class QueryWrapperBuild<T> {
    private QueryWrapper<T> queryWrapper;

    public static <T> QueryWrapperBuild<T> create(Class<T> claz) {
        QueryWrapperBuild build = new QueryWrapperBuild();
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.setEntityClass(claz);

        build.setQueryWrapper(queryWrapper);
        return build;
    }

    public QueryWrapperBuild<T> eq(String fieldName, Object value) {
        queryWrapper.eq(POUtils.getColumnName(queryWrapper.getEntityClass(), fieldName), value);
        return this;
    }

    public QueryWrapper<T> build() {
        return this.queryWrapper;
    }
}
