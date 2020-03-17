package com.luop;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 10辆车抢占三个车位
 */
public class SemaphoreDemo {

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);   //初始化3个车位，即并发数为3
        for (int i = 1; i <= 10; i++) {
            new Thread(() ->{
                try {
                    semaphore.acquire();   //获取资源(即车位)
                    System.out.println(Thread.currentThread().getName()+"抢占到了车位,准备去购物！");
                    TimeUnit.SECONDS.sleep(2);   //暂停线程，模拟用户停车后的行为
                    System.out.println(Thread.currentThread().getName()+"购物成功，驱车离开！");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();   //释放资源
                }

            },String.valueOf(i)).start();
        }
    }
}
