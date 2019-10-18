package com.imooc.sell.service;

import com.imooc.sell.dataobject.OrderMaster;
import com.imooc.sell.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderMasterService {

    /** 创建订单 **/
    OrderDTO createOrderMaster(OrderDTO orderDTO);

    /** 查询单个订单 **/
    OrderDTO queryOrderMaster(String orderId);

    /** 查询订单列表 **/
    Page<OrderDTO> queryOrderMaster(String buyerOpenid, Pageable pageable);

    /** 取消订单 **/
    OrderDTO cancelOrderMaster(OrderDTO orderDTO);

    /** 完结订单 **/
    OrderDTO finishOrderMaster(OrderDTO orderDTO);

    /** 支付订单 **/
    OrderDTO payOrderMaster(OrderDTO orderDTO);
}
