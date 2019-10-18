package com.imooc.sell.service;

import com.imooc.sell.dataobject.ProductInfo;
import com.imooc.sell.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductInfoService {

    ProductInfo findByProductId(String productId);

    /**
     * 查询所有商品
     * @return
     */
    List<ProductInfo> findAll();

    /**
     * 查询所有上架商品
     */
    List<ProductInfo> findUpAll();

    /**
     * 分页查询商品
     * @return
     */
    Page<ProductInfo> findAll(Pageable pageable);

    List<ProductInfo> findByProductName(String productName);

    ProductInfo saveProductInfo(ProductInfo productInfo);

    //TODO 加库存
    void increaseStock(List<CartDTO> cartDTOList);

    //TODO 减库存
    void decreaseStock(List<CartDTO> cartDTOList);

    //TODO 删除商品

    //TODO 更新商品
}
