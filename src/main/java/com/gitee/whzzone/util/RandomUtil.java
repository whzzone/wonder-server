package com.gitee.whzzone.util;

import java.util.Random;

//生成随机验证码
public class RandomUtil {

    private static final String CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz!+=@-";

    /**
     * 整型随机字符
     * @param length
     * @return
     */
    public static String getIntCode(int length){
        Random random = new Random();

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));
        }

        return String.valueOf(sb);
    }

    /**
     * 随机数字-字母-符号字符串
     * @param length
     * @return
     */
    public static String getStringCode(int length){
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }
        return sb.toString();
    }
}