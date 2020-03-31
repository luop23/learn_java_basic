package com.luop;

import java.util.Random;

/**
 * @Author: luoping
 * @Date: 2020/3/31 16:27
 * @Description: jvm参数
 */
public class learn_JvmParams {

    public static void main(String[] args) {
//        test1();
        test2();
    }

    //java.lang.OutOfMemoryError: Java heap space  堆内存溢出异常
    private static void test2() {
        StringBuilder str = new StringBuilder("成都");
        while (true) {
            str.append(new Random().nextInt(10000));
        }
    }

    //堆内存信息查看
    private static void test1() {
        int processors = Runtime.getRuntime().availableProcessors();   //处理器核数
        long totalMemory = Runtime.getRuntime().totalMemory();     //初始化堆内存大小(默认为本机物理内存的1/64)
        long maxMemory = Runtime.getRuntime().maxMemory();      //最大堆内存大小(默认为本机物理内存的1/4)
        System.out.println("processors==============" + processors + "核");
        System.out.println("-Xms:totalMemory==============" + totalMemory + "B，约为：" + totalMemory / (double) 1024 / 1024 + "MB");
        System.out.println("-Xmx:maxMemory==============" + maxMemory + "B，约为：" + maxMemory / (double) 1024 / 1024 + "MB");
    }
}
