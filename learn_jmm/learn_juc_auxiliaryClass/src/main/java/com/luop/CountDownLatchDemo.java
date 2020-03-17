package com.luop;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {

    /**
     * 教室有六个同学上自习，班长需等六个同学走完才能锁门
     */
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(6);   //子线程个数
        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + " 离开教室");
                latch.countDown();   //线程结束一个就减少一次
            }, String.valueOf(i)).start();
        }
        latch.await();   //阻塞主线程，直到子线程全部执行完
        System.out.println(Thread.currentThread().getName() + " 班长关门走人");
    }
}
