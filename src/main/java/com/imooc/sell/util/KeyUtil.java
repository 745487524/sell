package com.imooc.sell.util;

import java.util.Random;

public class KeyUtil {

    /** 生成唯一的主键
     * 格式：时间+主键
     * **/
    public static synchronized String genUniqueKey(){
        Random random = new Random();
        Integer keyNum = random.nextInt(900000) + 100000;
        return System.currentTimeMillis() + String.valueOf(keyNum);
    }
}
