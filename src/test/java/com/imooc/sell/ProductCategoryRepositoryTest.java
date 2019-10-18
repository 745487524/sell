package com.imooc.sell;

import com.imooc.sell.dataobject.ProductCategory;
import com.imooc.sell.repository.ProductCategoryRepository;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ProductCategoryRepositoryTest extends BaseTest {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Test
    public void findOneTest(){
        ProductCategory category = new ProductCategory();
        category = productCategoryRepository.findById(1).get();
        Assert.assertNotNull(category);
    }

    @Test
    public void saveTest(){
        ProductCategory category = new ProductCategory();
        category.setCategoryId(2);
        category.setCategoryName("女生最爱");
        category.setCategoryType(3);
        ProductCategory result = productCategoryRepository.save(category);
        System.out.println(result);
    }
}
