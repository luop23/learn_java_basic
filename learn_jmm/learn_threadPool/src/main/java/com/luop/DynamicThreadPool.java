package com.luop;

import java.util.concurrent.*;

/**
 * @Author: luoping
 * @Date: 2020/6/3 11:12
 * @Description: 动态设置线程池
 */
public class DynamicThreadPool {
    public static void main(String[] args) {
        dynamicModifyThreadPoolParams(10, 20, 88, new ThreadPoolExecutor.CallerRunsPolicy());
    }

    //自定义线程池
    private static ThreadPoolExecutor MyLocalThreadPool() {
        return new ThreadPoolExecutor(2, 5, 30, TimeUnit.SECONDS, new MyBlockingQueue<>(10),
                new NamedThreadFactory("订单业务线程池"), new ThreadPoolExecutor.AbortPolicy());
    }

    //动态修改线程池参数
    private static void dynamicModifyThreadPoolParams(int corePoolSize, int maximumPoolSize, int queueNum, RejectedExecutionHandler handler) {
        ThreadPoolExecutor threadPool = MyLocalThreadPool();
        for (int i = 0; i < 10; i++) {
            threadPool.execute(() -> System.out.println(Thread.currentThread().getName() + "==============>"));
        }
        System.out.println("线程池参数修改之前=======================");
        printThreadPoolInfo(threadPool);
        System.out.println("线程池参数修改之后=======================");
        threadPool.setCorePoolSize(corePoolSize);
        threadPool.setMaximumPoolSize(maximumPoolSize);
        threadPool.setRejectedExecutionHandler(handler);
        MyBlockingQueue queue = (MyBlockingQueue) threadPool.getQueue();
        queue.setCapacity(queueNum);
        threadPool.allowCoreThreadTimeOut(true);    //设置核心线程空闲时间达到超时时间时可关闭
        threadPool.prestartAllCoreThreads();    //预热所有核心线程
        threadPool.prestartCoreThread();    //预热一个核心线程
        printThreadPoolInfo(threadPool);
    }

    //打印参数
    private static void printThreadPoolInfo(ThreadPoolExecutor threadPoolExecutor) {
        BlockingQueue<Runnable> queue = threadPoolExecutor.getQueue();
        System.out.println("核心线程数大小：========>" + threadPoolExecutor.getCorePoolSize());
        System.out.println("最大线程数大小：========>" + threadPoolExecutor.getMaximumPoolSize());
        System.out.println("线程池中可用线程数：========>" + threadPoolExecutor.getPoolSize());
        System.out.println("线程池中活动线程数：========>" + threadPoolExecutor.getActiveCount());
        System.out.println("拒绝策略：========>" + threadPoolExecutor.getRejectedExecutionHandler());
        System.out.println("队列大小：========>" + (queue.size() + queue.remainingCapacity()));
        System.out.println("排队线程数：========>" + queue.size());
        System.out.println("队列剩余大小：========>" + queue.remainingCapacity());
    }

}
