package com.luop;

import java.util.concurrent.*;

/**
 * 集齐七颗龙珠，即可召唤神龙
 */
public class CyclicBarrierDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask futureTask = new FutureTask<>(new MyThread());
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7,futureTask);
//        CyclicBarrier cyclicBarrier = new CyclicBarrier(7,new Thread(() -> System.out.println("召唤神龙！")));
        for (int i = 1; i <= 7; i++) {
            new Thread(() ->{
                System.out.println("集齐第"+Thread.currentThread().getName()+"颗龙珠");
                try {
                    cyclicBarrier.await();   //底层原理  --count
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            },String.valueOf(i)).start();
        }
        System.out.println(futureTask.get());
    }
}

class MyThread implements Callable<String>{

    @Override
    public String call() throws Exception {
        return "召唤神龙！";
    }
}
