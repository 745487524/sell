package com.imooc.sell.service.impl;

import com.imooc.sell.converter.OrderMaster2OrderDTOConverter;
import com.imooc.sell.dataobject.OrderDetail;
import com.imooc.sell.dataobject.OrderMaster;
import com.imooc.sell.dataobject.ProductInfo;
import com.imooc.sell.dto.CartDTO;
import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.enums.OrderStatusEnum;
import com.imooc.sell.enums.PayStatusEnum;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.repository.OrderDetailRepository;
import com.imooc.sell.repository.OrderMasterRepository;
import com.imooc.sell.service.OrderMasterService;
import com.imooc.sell.service.ProductInfoService;
import com.imooc.sell.util.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderMasterServiceImpl implements OrderMasterService {

    @Autowired
    private ProductInfoService productInfoService;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Override
    @Transactional
    public OrderDTO createOrderMaster(OrderDTO orderDTO) {
        //1、查询商品数量，单价
        String orderId = KeyUtil.genUniqueKey();

        Integer statck = 0;
        BigDecimal amount = new BigDecimal(BigInteger.ZERO);
        List<OrderDetail> orderDetailList = orderDTO.getOrderDetailList();
        for(OrderDetail orderDetail:orderDetailList){
            ProductInfo productInfo = productInfoService.findByProductId(orderDetail.getProductId());
            if (productInfo == null){
                throw new SellException(ResultEnum.PRODUCT_NO_LIST);
            }
            //2、计算总价
            Integer num = orderDetail.getProductQuantity();
            BigDecimal price = orderDetail.getProductPrice();
            BigDecimal singleAmount = price.multiply(new BigDecimal(num));
            amount = amount.add(singleAmount);

            //写入订单数据库OrderDetail
            BeanUtils.copyProperties(productInfo, orderDetail);
            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            orderDetail.setOrderId(orderId);
            orderDetailRepository.save(orderDetail);
        }
        //3、写入订单数据库（OrderMaster）
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMaster.setOrderId(orderId);
        orderMaster.setOrderAmount(amount);
        orderMasterRepository.save(orderMaster);
        //4、订单完成减去库存
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
                .map(e -> new CartDTO(e.getProductId(),e.getProductQuantity()))
                .collect(Collectors.toList());
        productInfoService.decreaseStock(cartDTOList);
        return orderDTO;
    }

    @Override
    public OrderDTO queryOrderMaster(String orderId) {
        OrderDTO orderDTO = new OrderDTO();
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if (CollectionUtils.isEmpty(orderDetailList)) throw new SellException(ResultEnum.ORDERMASTER_NOT_EXIST);
        OrderMaster orderMaster = orderMasterRepository.findById(orderId).get();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> queryOrderMaster(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(buyerOpenid,pageable);
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.converter(orderMasterPage.getContent());
        Page<OrderDTO> orderDTOPage = new PageImpl<OrderDTO>(orderDTOList,pageable,orderMasterPage.getTotalElements());
        return orderDTOPage;
    }

    @Override
    @Transactional
    public OrderDTO cancelOrderMaster(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();
        //判断订单状态
        if (!(orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())||orderDTO.getOrderStatus().equals(OrderStatusEnum.GET.getCode()))){
            log.error("【订单状态异常】 订单状态异常，orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDERMASTER_STATUS_ERROR);
        }
        //更新订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster tempOrderMaster = orderMasterRepository.findById(orderMaster.getOrderId()).get();
        if (tempOrderMaster == null){
            log.error("【订单状态异常】 查询订单信息失败!!!! orderId={},payStatus={}",orderMaster.getOrderId(),orderMaster.getPayStatus());
            throw new SellException(ResultEnum.ORDERMASTER_INFORMATION_SELECT_ERROR);
        }
        orderMaster.setCreateTime(tempOrderMaster.getCreateTime());
        orderMaster.setUpdateTime(new Date());
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if (updateResult == null){
            log.error("【订单状态异常】 订单状态更新失败,orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDERMASTER_UPDATE_ERROR);
        }
        //返回库存
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("【订单状态异常】 订单中无商品详情,orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDERMASTER_DETAIL_ERROR);
        }
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
                .map(e -> new CartDTO(e.getProductId(),e.getProductQuantity()))
                .collect(Collectors.toList());
        productInfoService.increaseStock(cartDTOList);
        //如果已付款，需要退款
        if (orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())){
            //TODO 库存退款
        }
        return orderDTO;
    }

    @Override
    public OrderDTO finishOrderMaster(OrderDTO orderDTO) {
        //判断订单状态
        if (!(orderDTO.getOrderStatus().equals(OrderStatusEnum.GET.getCode())
                ||orderDTO.getOrderStatus().equals(OrderStatusEnum.DOING.getCode()))){
            log.error("【完结订单失败】,orderId={}，原orderStatus={}，预计更新orderStatus={}",orderDTO.getOrderId(),OrderStatusEnum.FINISHED.getCode());
            throw new SellException(ResultEnum.ORDERMASTER_FINISH_ERROR);
        }
        //更新订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster tempOrderMaster = orderMasterRepository.findById(orderMaster.getOrderId()).orElse(null);
        if (tempOrderMaster == null){
            log.error("【完结订单失败】 查询订单信息失败!!!! orderId={},payStatus={}",orderMaster.getOrderId(),orderMaster.getPayStatus());
            throw new SellException(ResultEnum.ORDERMASTER_INFORMATION_SELECT_ERROR);
        }
        orderMaster.setCreateTime(tempOrderMaster.getCreateTime());
        orderMaster.setUpdateTime(new Date());
        OrderMaster result = orderMasterRepository.save(orderMaster);
        if (result == null){
            log.error("【完结订单失败】,orderId={},原orderStatus={},预计更新orderStatus={}",orderDTO.getOrderId(),OrderStatusEnum.FINISHED.getCode());
            throw new SellException(ResultEnum.ORDERMASTER_FINISH_UPDATE_ERROR);
        }
        return orderDTO;
    }

    @Override
    public OrderDTO payOrderMaster(OrderDTO orderDTO) {
        //查看支付状态
        if (!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())){
            log.error("【支付订单失败】orderId={},payStatus={}",orderDTO.getOrderId(),orderDTO.getPayStatus());
            throw new SellException(ResultEnum.ORDERMASTER_PAY_STATUS_ERROR);
        }
        //更新支付状态
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster tempOrderMaster = orderMasterRepository.findById(orderMaster.getOrderId()).orElse(null);
        if (tempOrderMaster == null){
            log.error("【支付订单失败】 查询订单信息失败!!!! orderId={},payStatus={}",orderMaster.getOrderId(),orderMaster.getPayStatus());
            throw new SellException(ResultEnum.ORDERMASTER_INFORMATION_SELECT_ERROR);
        }
        orderMaster.setCreateTime(tempOrderMaster.getCreateTime());
        orderMaster.setUpdateTime(new Date());
        OrderMaster updateOrderMaster = orderMasterRepository.save(orderMaster);
        if (updateOrderMaster == null){
            log.error("【支付订单失败】orderId={},payStatus={}",orderDTO.getOrderId(),orderDTO.getPayStatus());
            throw new SellException(ResultEnum.ORDERMASTER_PAY_UPDATE_ERROR);
        }
        return orderDTO;
    }
}
