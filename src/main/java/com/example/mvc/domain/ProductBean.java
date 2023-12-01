package com.example.mvc.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.mvc.dao.SubMapper;
import com.github.dreamyoung.mprelation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;


import java.io.Serializable;
import java.util.List;

//声明该实体类映射的表名
@Data
@Builder
@FieldNameConstants
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_center_group")
public class ProductBean implements Serializable {
    //表示该列为主键列，value表示该列映射的列名
    //type = IdType.AUTO 表示该列的值使用自动增长列生成
    //@TableId(value = "id")
    private String id;

    //指定当前属性映射的列名
    private String name;

    @TableField("stafferId")
    private String stafferId;

    @TableField("type")
    private Integer ptype;

    @TableField(exist = false)
    private List<SubBean> subBeanList;

}