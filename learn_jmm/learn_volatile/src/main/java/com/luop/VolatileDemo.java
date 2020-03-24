package com.luop;

/**
 * @Author: luoping
 * @Date: 2020/3/23 17:37
 * @Description:
 */

import java.util.concurrent.TimeUnit;

/**
 * volatile特性理解
 */
public class VolatileDemo {
    public static void main(String[] args) {
        test1();
    }

    //验证volatile的可见性
    private static void test1() {
        MyData myData = new MyData();
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);   //模拟执行过程
                myData.addNUm();
                System.out.println(Thread.currentThread().getName() + "获取的结果：" + myData.getNum());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "A").start();
        while (myData.getNum() == 0) {

        }
        System.out.println(Thread.currentThread().getName() + "获取的结果:" + myData.getNum());
    }
}

class MyData {
    private volatile int num = 0;

    public void addNUm() {
        num += 20;
    }

    public int getNum() {
        return num;
    }
}
