package com.example.mvc.demo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class UserDomain implements Serializable {
    private String id;
    private String name;
    private Integer age;

}
