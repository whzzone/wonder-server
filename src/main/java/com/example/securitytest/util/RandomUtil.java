package com.example.securitytest.util;

import java.util.Random;

//生成随机验证码
public class RandomUtil {
    /**
     * 验证码位数
     * @param num
     * @return
     */
    public static String getCode(int num){
        Random random = new Random();

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < num; i++) {
            sb.append(random.nextInt(10));
        }

        return String.valueOf(sb);
    }
}