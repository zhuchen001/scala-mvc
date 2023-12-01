package com.example.mvc;

import java.lang.reflect.Type;
import java.lang.reflect.ParameterizedType;

public class GenericClass<T> {
    private Class<T> type;

    public GenericClass() {
        // 获取泛型参数的实际类型
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        type = (Class<T>) pt.getActualTypeArguments()[0];
    }

    public void printType() {
        System.out.println("实际类型为: " + type.getName());
    }

    public static void main(String[] args) {
        GenericClass<String> demo = new GenericClass<String>();

        demo.printType();
    }
}

