package com.imooc.sell.util;

import com.imooc.sell.enums.Result;
import com.imooc.sell.vo.ProductListVO;

public class ResultVOUtil {

    public static ProductListVO success(Object data){
        ProductListVO productListVO = new ProductListVO();
        productListVO.setCode(Result.SUCCESS.getCode());
        productListVO.setMsg(Result.SUCCESS.getMessage());
        productListVO.setData(data);
        return productListVO;
    }

    public static ProductListVO success(){
        return success(null);
    }

    public static ProductListVO error(Integer code, String msg){
        ProductListVO productListVO = new ProductListVO();
        productListVO.setCode(Result.ERROR.getCode());
        productListVO.setMsg(Result.ERROR.getMessage());
        productListVO.setData(msg);
        return productListVO;
    }
}
