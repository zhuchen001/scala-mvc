package com.example.mvc.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

//声明该实体类映射的表名
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO implements Serializable {

    private String id;

    //指定当前属性映射的列名
    private String name;


    private String stafferId;


    private Integer ptype;

    private List<SubBean> subBeanList;

}