package com.imooc.sell.service.impl;

import com.imooc.sell.dataobject.ProductCategory;
import com.imooc.sell.repository.ProductCategoryRepository;
import com.imooc.sell.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryRepository repository;
    @Override
    public ProductCategory findByCategoryId(String categoryId) {
        return repository.findById(Integer.getInteger(categoryId)).get();
    }

    @Override
    public List<ProductCategory> findByCategoryType(String categoryType) {
        return repository.findByCategoryType(categoryType);
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypes) {
        return repository.findByCategoryTypeIn(categoryTypes);
    }

    @Override
    public List<ProductCategory> findAll() {
        return repository.findAll();
    }

    @Override
    public ProductCategory saveProductCategory(ProductCategory category) {
        return repository.save(category);
    }
}
