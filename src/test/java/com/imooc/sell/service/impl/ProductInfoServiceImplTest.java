package com.imooc.sell.service.impl;

import com.imooc.sell.BaseTest;
import com.imooc.sell.dataobject.ProductInfo;
import com.imooc.sell.enums.ProductStatusEnum;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

public class ProductInfoServiceImplTest extends BaseTest {

    @Autowired
    private ProductInfoServiceImpl service;
    @Test
    public void findByProductId() {
        ProductInfo productInfo = service.findByProductId("123456");
        Assert.assertEquals("123456",productInfo.getProductId());
    }

    @Test
    public void findAll() {
        List<ProductInfo> productInfos = service.findAll();
        Assert.assertNotEquals(0,productInfos.size());
    }

    @Test
    public void findUpAll() {
        List<ProductInfo> productInfoList = service.findUpAll();
        Assert.assertNotEquals(0,productInfoList.size());
    }

    @Test
    public void findAll1() {
        PageRequest request = PageRequest.of(0,2);
        Page<ProductInfo> page = service.findAll(request);
        System.out.println(page.getTotalElements());
    }

    @Test
    public void findByProductName() {
        List<ProductInfo> productInfos =service.findByProductName("皮蛋粥");
        Assert.assertNotEquals(0,productInfos.size());
    }

    @Test
    public void saveProductInfo() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("1");
        productInfo.setProductName("皮皮虾");
        productInfo.setProductPrice(new BigDecimal(10.2));
        productInfo.setCategoryType(2);
        productInfo.setProductDescription("皮皮的虾");
        productInfo.setProductStatus(ProductStatusEnum.UP.getStatus());
        productInfo.setProductStack(10);
        productInfo.setProductIcon("https://xxxxx.jpg");
        ProductInfo result = service.saveProductInfo(productInfo);
        System.out.println(result);
    }
}