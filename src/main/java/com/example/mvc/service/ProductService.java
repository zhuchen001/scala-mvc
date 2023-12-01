package com.example.mvc.service;

import com.example.mvc.domain.ProductBean;

public interface ProductService {
    /**
     * 保存 Product
     *
     * @param domain
     * @return
     */
    boolean saveProduct(ProductBean domain) throws Exception;


    boolean deleteProduct(String id);

    ProductBean findById(String id);
}
