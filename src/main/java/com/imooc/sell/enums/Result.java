package com.imooc.sell.enums;

import lombok.Data;
import lombok.Getter;

@Getter
public enum Result {
    SUCCESS(0,"成功"),
    ERROR(1,"失败")
    ;

    private Integer code;

    private String message;

    Result(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
