package com.example.mvc.scala;

import com.alibaba.fastjson.JSON;

public abstract class JSONTools {

    public static String toJson(Object obj) {
        return JSON.toJSONString(obj);
    }

    public static <T> T parser(String str, Class<T> claz){
        return JSON.parseObject(str, claz);
    }
}
