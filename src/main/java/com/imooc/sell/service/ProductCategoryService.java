package com.imooc.sell.service;

import com.imooc.sell.dataobject.ProductCategory;

import java.util.List;

public interface ProductCategoryService {

    ProductCategory findByCategoryId(String categoryId);

    List<ProductCategory> findByCategoryType (String categoryType);

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypes);

    List<ProductCategory> findAll();

    ProductCategory saveProductCategory(ProductCategory category);

}
