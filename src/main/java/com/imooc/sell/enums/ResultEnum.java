package com.imooc.sell.enums;

import lombok.Getter;

@Getter
public enum  ResultEnum {

    PRODUCT_NO_LIST(-1,"商品不存在"),
    PRODUCT_STOCK_ERROR(-2,"商品库存错误"),
    ORDERMASTER_NOT_EXIST(-3,"订单不存在"),
    ORDERMASTER_STATUS_ERROR(-4,"订单状态异常"),
    ORDERMASTER_UPDATE_ERROR(-5,"订单状态更新失败"),
    ORDERMASTER_DETAIL_ERROR(-6,"订单详情状态异常"),
    ORDERMASTER_FINISH_ERROR(-7,"订单完结异常"),
    ORDERMASTER_FINISH_UPDATE_ERROR(-8,"订单完结更新状态异常"),
    ORDERMASTER_PAY_STATUS_ERROR(-9,"订单支付状态异常"),
    ORDERMASTER_INFORMATION_SELECT_ERROR(-10,"订单信息查询异常"),
    ORDERMASTER_PAY_UPDATE_ERROR(-10,"订单支付状态更新异常")
    ;

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
