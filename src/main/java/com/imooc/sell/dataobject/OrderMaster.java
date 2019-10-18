package com.imooc.sell.dataobject;

import com.imooc.sell.enums.OrderStatusEnum;
import com.imooc.sell.enums.PayStatusEnum;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@DynamicInsert(value = true)
@DynamicUpdate(value = true)
public class OrderMaster {

    /** 订单id **/
    @Id
    private String orderId;
    /** 买家姓名 **/
    private String buyerName;
    /** 买家电话 **/
    private String buyerPhone;
    /** 买家地址 **/
    private String buyerAddress;
    /** 买家openId **/
    private String buyerOpenid;
    /** 订单总金额 **/
    private BigDecimal orderAmount;
    /** 订单状态 0:新下单 **/
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();
    /** 支付状态 0:未支付**/
    private Integer payStatus = PayStatusEnum.WAIT.getCode();
    /** 创建时间 **/
    @CreatedDate
    private Date createTime;
    /** 更新时间 **/
    @LastModifiedDate
    private Date updateTime;

}
