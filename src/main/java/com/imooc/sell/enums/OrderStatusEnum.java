package com.imooc.sell.enums;

import lombok.Getter;

@Getter
public enum OrderStatusEnum {
    NEW(0,"等待商家接单"),
    FINISHED(1,"订单完成"),
    CANCEL(2,"订单取消"),
    GET(3,"商家已接单"),
    DOING(4,"订单进行中")
    ;

    private Integer code;

    private String msg;

    OrderStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
