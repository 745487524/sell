package com.imooc.sell.repository;

import com.imooc.sell.BaseTest;
import com.imooc.sell.dataobject.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

public class ProductInfoRepositoryTest extends BaseTest {

    @Autowired
    private ProductInfoRepository repository;

    @Test
    public void saveTest(){
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("123456");
        productInfo.setProductName("皮蛋粥");
        productInfo.setProductPrice(new BigDecimal(3.2));
        productInfo.setProductDescription("很好喝的粥");
        productInfo.setProductIcon("https://xxx.jpg");
        productInfo.setCategoryType(1);
        productInfo.setProductStack(10);
        productInfo.setProductStatus(0);
        ProductInfo result = repository.save(productInfo);
        System.out.println(result);
    }

    @Test
    public void findByProductStatusTest(){
        List<ProductInfo> results = repository.findByProductStatus(0);
        Assert.assertNotEquals(0,results);
    }

}