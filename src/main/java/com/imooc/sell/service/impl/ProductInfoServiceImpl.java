package com.imooc.sell.service.impl;

import com.imooc.sell.dataobject.OrderMaster;
import com.imooc.sell.dataobject.ProductInfo;
import com.imooc.sell.dto.CartDTO;
import com.imooc.sell.enums.ProductStatusEnum;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.repository.OrderMasterRepository;
import com.imooc.sell.repository.ProductInfoRepository;
import com.imooc.sell.service.ProductInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class ProductInfoServiceImpl implements ProductInfoService {

    @Autowired
    private ProductInfoRepository repository;

    @Override
    public ProductInfo findByProductId(String productId) {
        return repository.findById(productId).get();
    }

    @Override
    public List<ProductInfo> findAll() {
        return repository.findAll();
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return repository.findByProductStatus(ProductStatusEnum.UP.getStatus());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public List<ProductInfo> findByProductName(String productName) {
        return repository.findByProductName(productName);
    }

    @Override
    public ProductInfo saveProductInfo(ProductInfo productInfo) {
        return repository.save(productInfo);
    }

    @Override
    @Transactional
    public void increaseStock(List<CartDTO> cartDTOList) {
        //查询订单
        for (CartDTO cartDTO:cartDTOList){
            ProductInfo productInfo = repository.findById(cartDTO.getProductId()).get();
            if (productInfo == null){
                log.error("【increase stock error】订单库存异常，productId={}",cartDTO.getProductId());
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            //更新库存信息
            Integer stock = productInfo.getProductStack() + cartDTO.getProductQuantity();
            if (stock < 0) {
                log.error("【increase stack error】商品库存异常,productId={}",cartDTO.getProductId());
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            productInfo.setProductStack(stock);
            repository.save(productInfo);
        }

    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO:cartDTOList){
            ProductInfo productInfo = repository.findById(cartDTO.getProductId()).get();
            if (productInfo == null) {
                log.error("【decrease stack error】没有找到商品详情,productId={}",cartDTO.getProductId());
                throw new SellException(ResultEnum.PRODUCT_NO_LIST);
            }
            Integer content = productInfo.getProductStack() - cartDTO.getProductQuantity();
            if (content < 0) {
                log.error("【decrease stack error】商品库存异常,productId={}",cartDTO.getProductId());
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            productInfo.setProductStack(content);
            repository.save(productInfo);
        }
    }
}
