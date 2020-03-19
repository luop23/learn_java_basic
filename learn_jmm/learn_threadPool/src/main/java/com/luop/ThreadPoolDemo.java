package com.luop;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 创建线程的三种方式
 */
public class ThreadPoolDemo {
    public static void main(String[] args) {
//        test1();
//        test2();
        test3();
    }

    //创建带缓存的线程池
    private static void test3() {
        ExecutorService pool = Executors.newCachedThreadPool();
        try {
            for (int i = 0; i < 10; i++) {
                pool.execute(() ->{
                    System.out.println(Thread.currentThread().getName()+"\t 办理业务");
                    try {
                        TimeUnit.MILLISECONDS.sleep(100);  //模拟业务办理
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.shutdown();
        }
    }

    //创建单个线程的线程池
    private static void test2() {
        ExecutorService pool = Executors.newSingleThreadExecutor();
        try {
            for (int i = 0; i < 10; i++) {
                pool.execute(() ->{
                    System.out.println(Thread.currentThread().getName()+"\t 办理业务");
                    try {
                        TimeUnit.MILLISECONDS.sleep(200);  //模拟业务办理
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.shutdown();
        }
    }

    //创建指定数量的线程池
    private static void test1() {
        ExecutorService pool = Executors.newFixedThreadPool(5);
        try {
            for (int i = 0; i < 10; i++) {
                pool.execute(() -> {
                    System.out.println(Thread.currentThread().getName()+"\t 办理业务");
                    try {
                        TimeUnit.MILLISECONDS.sleep(300);   //模拟业务执行
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally{
            pool.shutdown();   //关闭线程池
        }
    }
}
