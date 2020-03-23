package com.luop;

import java.util.concurrent.*;

/**
 * 创建线程的三种方式
 */
public class ThreadPoolDemo {
    public static void main(String[] args) {
//        test1();
//        test2();
//        test3();
        test4();
    }

    //自定义线程池
    private static void test4() {
        int processors = Runtime.getRuntime().availableProcessors();   //cpu处理器核数
        ExecutorService pool = new ThreadPoolExecutor(
                3,
                processors + 1,   //一般是处理器核数+1
                60, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy()     //执行策略，由调用者执行
        );
        try {
            for (int i = 0; i < 13; i++) {
                pool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "\t 办理业务");
                });
            }
        } finally {
            pool.shutdown();
        }
    }

    //创建带缓存的线程池
    private static void test3() {
        ExecutorService pool = Executors.newCachedThreadPool();   //具体的线程数量取决于系统分配
        try {
            for (int i = 0; i < 10; i++) {
                pool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "\t 办理业务");
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
                pool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "\t 办理业务");
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
                    System.out.println(Thread.currentThread().getName() + "\t 办理业务");
                    try {
                        TimeUnit.MILLISECONDS.sleep(300);   //模拟业务执行
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pool.shutdown();   //关闭线程池
        }
    }
}
