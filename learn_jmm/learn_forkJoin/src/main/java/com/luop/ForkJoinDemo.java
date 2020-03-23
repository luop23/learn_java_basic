package com.luop;


import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.*;

/**
 * 计算0-10000000000的值
 */
public class ForkJoinDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        new Thread(ForkJoinDemo::test1).start();
//        new Thread(ForkJoinDemo::test2).start();
        test3();
    }

    private static void test3() throws ExecutionException, InterruptedException {
        CompletableFuture future = CompletableFuture.supplyAsync(() -> {
            System.out.println("异步调用，有返回值");
            return 8888;
        });
        System.out.println("这是主线程2");
        future.whenComplete((t, u) -> {
            System.out.println(u);
            System.out.println(t);
        }).exceptionally(e -> {
            System.out.println();
            return 4444;
        }).get();
        System.out.println("这是主线程1");
    }

    //forkJoin框架实现
    private static void test2() {
        //创建任务
        LocalDateTime beginTime = LocalDateTime.now();
        MyTask task = new MyTask(0, 10000000000L);
        //创建forkJoin池
        ForkJoinPool pool = new ForkJoinPool();
        //调用方法
        ForkJoinTask<Long> joinTask = pool.submit(task);
        try {
            System.out.println("forkjoin结果：" + joinTask.get());   //获取计算结果
            LocalDateTime endTime = LocalDateTime.now();
            System.out.println("forkjoin耗时：" + Duration.between(beginTime, endTime).toMillis() + "毫秒");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            pool.shutdown();
        }
    }

    //传统方法
    private static void test1() {
        LocalDateTime beginTime = LocalDateTime.now();
        long result = 0L;
        for (long i = 0; i <= 10000000000L; i++) {
            result += i;
        }
        LocalDateTime endTIme = LocalDateTime.now();
        Duration duration = Duration.between(beginTime, endTIme);
        System.out.println("普通方法结果：" + result);
        System.out.println("普通方法耗时：" + duration.toMillis() + "毫秒");
    }
}

class MyTask extends RecursiveTask<Long> {
    private static final int ADJUST_VALUE = 100000;    //按每份100将1000000000进行分份

    private long begin;   //起始

    private long end;    //结束

    private long result;   //结果

    public MyTask(long begin, long end) {
        this.begin = begin;
        this.end = end;
    }

    @Override
    protected Long compute() {
        if ((end - begin) <= ADJUST_VALUE) {   //起始位置之间小于100直接计算
            for (long i = begin; i <= end; i++) {
                result += i;
            }
        } else {
            long middle = (begin + end) / 2L;
            MyTask task1 = new MyTask(begin, middle);
            MyTask task2 = new MyTask(middle + 1, end);
            task1.fork();  //fork()方法会递归调用compute()方法
            task2.fork();
            result = task1.join() + task2.join();
        }
        return result;
    }
}
