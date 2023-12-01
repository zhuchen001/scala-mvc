package com.example.mvc.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;

//声明该实体类映射的表名
@Data
@Builder
@FieldNameConstants
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("t_center_group_sub")
public class SubBean implements Serializable {
    //表示该列为主键列，value表示该列映射的列名
    //type = IdType.AUTO 表示该列的值使用自动增长列生成
    //@TableId(value = "id")
    @TableId(type = IdType.AUTO)
    private String id;

    //指定当前属性映射的列名
    private String name;

    @TableField("parentId")
    private String parentId;

}