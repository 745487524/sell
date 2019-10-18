package com.imooc.sell.repository;

import com.imooc.sell.dataobject.ProductCategory;
import com.imooc.sell.dataobject.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Integer> {

    List<ProductCategory> findByCategoryType(String categoryType);

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypes);
}
