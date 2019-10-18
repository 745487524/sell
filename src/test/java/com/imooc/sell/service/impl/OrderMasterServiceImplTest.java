package com.imooc.sell.service.impl;

import com.imooc.sell.BaseTest;
import com.imooc.sell.dataobject.OrderDetail;
import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.enums.OrderStatusEnum;
import com.imooc.sell.enums.PayStatusEnum;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.repository.OrderDetailRepository;
import com.imooc.sell.service.OrderMasterService;
import com.imooc.sell.util.KeyUtil;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class OrderMasterServiceImplTest extends BaseTest {

    @Autowired
    private OrderMasterService orderMasterService;
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    private static final String OPEN_ID = "openid";

    private static final String ORDER_ID = "1571188774726264429";

    @Test
    public void createOrderMaster() {
        OrderDTO orderDTO = new OrderDTO();
        String orderId = KeyUtil.genUniqueKey();
        orderDTO.setOrderId(orderId);
        orderDTO.setBuyerName("阿里巴巴");
        orderDTO.setBuyerAddress("阿里巴巴的地址");
        orderDTO.setBuyerOpenid(OPEN_ID);
        orderDTO.setBuyerPhone("123456");
        orderDTO.setOrderAmount(new BigDecimal(159));
        orderDTO.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderDTO.setPayStatus(PayStatusEnum.WAIT.getCode());

        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail o1 = new OrderDetail();
        o1.setOrderId(orderId);
        o1.setDetailId(KeyUtil.genUniqueKey());
        o1.setProductIcon("http://xxxxx.jpg");
        o1.setProductId("1");
        o1.setProductName("皮蛋瘦肉");
        o1.setProductPrice(new BigDecimal(159));
        o1.setProductQuantity(10);
        orderDetailRepository.save(o1);

        orderDetailList.add(o1);

        orderDTO.setOrderDetailList(orderDetailList);
        OrderDTO result = orderMasterService.createOrderMaster(orderDTO);
        Assert.assertNotNull(result);
    }

    @Test
    public void queryOrderMaster() {
        Page<OrderDTO> orderDTOPage = orderMasterService.queryOrderMaster(OPEN_ID,PageRequest.of(0,2));
        Assert.assertNotEquals(0,orderDTOPage.getTotalElements());
    }

    @Test
    public void queryOrderMaster1() {
    }

    @Test
    public void cancelOrderMaster() {
        OrderDTO orderDTO = orderMasterService.queryOrderMaster(ORDER_ID);
        OrderDTO result = orderMasterService.cancelOrderMaster(orderDTO);
        Assert.assertEquals(OrderStatusEnum.CANCEL.getCode(),result.getOrderStatus());
    }

    @Test
    public void finishOrderMaster() {
        OrderDTO orderDTO = orderMasterService.queryOrderMaster(ORDER_ID);
        OrderDTO result = orderMasterService.finishOrderMaster(orderDTO);
        Assert.assertEquals(OrderStatusEnum.FINISHED.getCode(),result.getOrderStatus());
    }

    @Test
    public void payOrderMaster() {
        OrderDTO orderDTO = orderMasterService.queryOrderMaster(ORDER_ID);
        OrderDTO result = orderMasterService.payOrderMaster(orderDTO);
        Assert.assertEquals(PayStatusEnum.SUCCESS.getCode(),result.getPayStatus());
    }
}