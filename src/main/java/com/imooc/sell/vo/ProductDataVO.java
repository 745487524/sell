package com.imooc.sell.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ProductDataVO {

    /***
     * 类目名称
     * */
    @JsonProperty("name")
    private String categoryName;
    /**
     * 类目类型
     * **/
    @JsonProperty("type")
    private Integer categoryType;

    @JsonProperty("foods")
    private List<ProductInfoVO> productFoods;

}
