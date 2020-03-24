package com.luop;

/**
 * @Author: luoping
 * @Date: 2020/3/23 17:37
 * @Description:
 */

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * volatile特性理解
 */
public class VolatileDemo {
    public static void main(String[] args) {
//        test1();
//        test2();
        test3();
    }

    //非原子性解决方案
    private static void test3() {
        MyData myData = new MyData();
        CountDownLatch latch = new CountDownLatch(20);   //子线程个数
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                for (int j = 0; j < 2000; j++) {
                    myData.numPlusPlus2();
                }
                latch.countDown();
            }).start();
        }
        try {
            latch.await();   //阻塞主线程  直到所有子线程全部执行完毕
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("num的理论值是：40000;实际值是：" + myData.getNum2());
    }

    //验证volatile的非原子性
    private static void test2() {
        MyData myData = new MyData();
        CountDownLatch latch = new CountDownLatch(20);   //子线程个数
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                for (int j = 0; j < 2000; j++) {
                    myData.numPlusPlus();
                }
                latch.countDown();
            }).start();
        }
        try {
            latch.await();   //阻塞主线程  直到所有子线程全部执行完毕
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("num的理论值是：40000;实际值是：" + myData.getNum());
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

    AtomicInteger num2 = new AtomicInteger(0);   //初始值为0的原子性的Integer

    public void addNUm() {
        num += 20;
    }

    public int getNum() {
        return num;
    }

    public void numPlusPlus() {
        num++;
    }

    public void numPlusPlus2() {
        num2.getAndIncrement();
    }

    public AtomicInteger getNum2() {
        return num2;
    }
}
