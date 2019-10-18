package com.imooc.sell.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Data
@DynamicUpdate
public class ProductInfo {

    /** 商品编号 **/
    @Id
    private String productId;

    /** 商品名称 **/
    private String productName;

    /** 商品单价 **/
    private BigDecimal productPrice;

    /** 商品库存 **/
    private Integer productStack;

    /** 商品描述 **/
    private String productDescription;

    /** 缩略图 **/
    private String productIcon;

    /** 商品上架状态 0:正常；1:下架 **/
    private Integer productStatus;

    /** 商品类目 **/
    private Integer categoryType;

    @Override
    public String toString() {
        return "ProductInfo{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                ", productStack=" + productStack +
                ", productDescription='" + productDescription + '\'' +
                ", productIcon='" + productIcon + '\'' +
                ", productStatus=" + productStatus +
                ", categoryType='" + categoryType + '\'' +
                '}';
    }
}
