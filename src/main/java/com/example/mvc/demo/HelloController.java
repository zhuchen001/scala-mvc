package com.example.mvc.demo;

import com.example.mvc.dao.ProductMapper;
import com.example.mvc.domain.ProductBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class HelloController {
    @Resource
    private ProductMapper productMapper;

    @RequestMapping("/index")
    public String index() {
        List<ProductBean> productBeans = productMapper.selectList(null);
        return "welcome" + productBeans;
    }
}