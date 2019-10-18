package com.imooc.sell.enums;

import lombok.Getter;

@Getter
public enum ProductStatusEnum {
    UP(0,"上架状态"),
    DOWN(1,"下架状态")
    ;

    /**
     * 状态码
     */
    private Integer status;

    /**
     * 状态内容
     */
    private String message;

    private ProductStatusEnum(Integer status, String message){
        this.status = status;
        this.message = message;
    }
}
