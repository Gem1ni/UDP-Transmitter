package com.gem1ni.udp.util;

/**
 * 控制台Log输出工具类
 * Created by GemIni on 2016/9/22.
 */

public class L {

    private static final boolean DEBUG = false;

    public static void out(Object object) {
        if (DEBUG)
            System.out.println(object);
    }

    public static void err(String object) {
        if (DEBUG)
            System.err.println(object);
    }
}
