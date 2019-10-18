package com.imooc.sell.controller;

import com.imooc.sell.dataobject.ProductCategory;
import com.imooc.sell.dataobject.ProductInfo;
import com.imooc.sell.service.ProductCategoryService;
import com.imooc.sell.service.ProductInfoService;
import com.imooc.sell.util.ResultVOUtil;
import com.imooc.sell.vo.ProductDataVO;
import com.imooc.sell.vo.ProductInfoVO;
import com.imooc.sell.vo.ProductListVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/buyer/product")
public class BuyProductController {

    @Autowired
    private ProductInfoService productInfoService;
    @Autowired
    private ProductCategoryService productCategoryService;

    @GetMapping("/list")
    private ProductListVO list(){
        //1、查询所有上架商品
        List<ProductInfo> productInfos = productInfoService.findUpAll();

        //2、查询类目(一次性查询)
//        List<Integer> categoryListType = new ArrayList<>();
//        for (ProductInfo productInfo:
//             productInfos) {
//            categoryListType.add(productInfo.getCategoryType());
//        }
        List<Integer> categoryTypeList = productInfos.stream()
                .map(e -> e.getCategoryType())
                .collect(Collectors.toList());
        List<ProductCategory> categoryList = productCategoryService.findByCategoryTypeIn(categoryTypeList);
        //3、数据拼装
        ArrayList<ProductDataVO> productDataVOList = new ArrayList<>();
        for (ProductCategory productCategory:categoryList){
            ProductDataVO dataVO = new ProductDataVO();
            dataVO.setCategoryType(productCategory.getCategoryType());
            dataVO.setCategoryName(productCategory.getCategoryName());
            ArrayList<ProductInfoVO> productInfoVOList = new ArrayList<>();
            for (ProductInfo productInfo : productInfos){
                ProductInfoVO productInfoVO = new ProductInfoVO();
                BeanUtils.copyProperties(productInfo, productInfoVO);
                productInfoVOList.add(productInfoVO);
            }
            dataVO.setProductFoods(productInfoVOList);
            productDataVOList.add(dataVO);
        }
        return ResultVOUtil.success(productDataVOList);
    }
}
