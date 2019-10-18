package com.imooc.sell.enums;

import lombok.Getter;

@Getter
public enum PayStatusEnum {

    WAIT(0,"等待支付"),
    SUCCESS(1,"成功支付"),
    CANCEL(2,"取消支付"),
    CALLBACK(2,"已退款")
    ;

    private Integer code;

    private String msg;

    PayStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
