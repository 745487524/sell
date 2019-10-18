package com.imooc.sell.repository;

import com.imooc.sell.BaseTest;
import com.imooc.sell.dataobject.OrderMaster;
import com.imooc.sell.enums.OrderStatusEnum;
import com.imooc.sell.enums.PayStatusEnum;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class OrderMasterRepositoryTest extends BaseTest {

    @Autowired
    private OrderMasterRepository repository;

    @Test
    public void saveTest(){
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("3");
        orderMaster.setBuyerName("阿里4");
        orderMaster.setBuyerAddress("去幼儿园的路");
        orderMaster.setBuyerOpenid("openid");
        orderMaster.setBuyerPhone("15903303314");
        orderMaster.setOrderAmount(new BigDecimal(159));
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMaster.setCreateTime(repository.findById("3").get().getCreateTime());
        orderMaster.setUpdateTime(new Timestamp(new Date().getTime()));
        OrderMaster result = repository.save(orderMaster);
        System.out.println(result);
    }

    @Test
    public void findByOrderId(){
//        Page<OrderMaster> orderMasters = repository.findByBuyerOpenid("openid",PageRequest.of(0,2));
        OrderMaster orderMaster = repository.findById("1571188774726264429").get();
        System.out.println(orderMaster);
//        Assert.assertNotEquals(0,orderMasters.getTotalElements());

    }

}