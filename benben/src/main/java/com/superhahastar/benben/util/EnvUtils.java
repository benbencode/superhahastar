package com.superhahastar.benben.util;

public class EnvUtils {
    /**
     * 判断本地环境为windows还是liunx
     * @return
     */
    public static Boolean Iswindows() {
        if (System.getProperty("os.name").toLowerCase().startsWith("windows") | System.getProperty("os.name").toLowerCase().startsWith("mac")) {
            return true;
        } else {
            return false;
        }

    }
}
