package com.lz.sword.common.utils;

import java.util.Random;

/**
 * 生成随机字符串
 *
 * @author liangzhong.tan
 * @version 1.0 2020-04-03 22:27
 */
public final class RandomGenerateUtil {

    private static Random getR = new Random();

    /**
     * 随机字符串
     *
     * @param type   1: 数字, 其他: 字母
     * @param length 长度
     * @return 字符串
     */
    public static String getRandomS(int type, int length) {
        StringBuilder sb = new StringBuilder();
        if (type == 1) {
            for (int i = 0; i < length; i++) {
                sb.append(getSz());
            }
        } else {
            for (int i = 0; i < length; i++) {
                int ri = getR.nextInt(2);
                sb.append(ri == 0 ? getSz() : getZm());
            }
        }
        return sb.toString();
    }

    /**
     * 字母
     *
     * @return
     */
    private static String getZm() {
        char sSS = (char) (getR.nextInt(26) + 97);
        char sBs = (char) (getR.nextInt(26) + 65);
        char[] sTemp = {sSS, sBs};
        int i = getR.nextInt(2);
        return String.valueOf(sTemp[i]);
    }

    /**
     * 数字
     *
     * @return
     */
    private static String getSz() {
        int getI = getR.nextInt(10) + 48;
        return String.valueOf((char) getI);
    }

    public static void main(String[] args) {
        System.out.println(getRandomS(0,7));
        System.out.println(getZm());
        System.out.println(getSz());
    }
}