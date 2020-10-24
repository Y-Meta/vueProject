package com.plmm.utils;

import java.util.Random;

/**
 * @version 1.0
 * @date 2020/3/16 9:56
 */
public class RequestIdUtil {

    public static final String PREFIX="DSP";
    /**
     * 根据默认前缀产生主键策略
     * @return
     */
    public static String generateNumber() {
       return generateNumber(PREFIX);
    }

    /**
     * 按照指定前缀生成主键策略
     * @param prefix
     * @return
     */
    public static String generateNumber(String prefix){
        // 带毫秒时间字符串
        String date = String.valueOf(System.currentTimeMillis());
        Random ran = new Random();
        String random = String.valueOf(ran.nextInt(1000));
        return prefix+ date + random;
    }
}
