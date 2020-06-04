package com.luop;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class DemoCallable {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        /*
         *new Thread() 中传入FutureTask类型的原因：
         * new Thread() 括号中需要传入的是Runnable接口类型，但是MyThread是Callable接口
         * 直接传入MyThread实例会报错，所以需要借助FutureTask类型实例作为中间类型进行传递
         * 因为FutureTask类型实现了Runnable接口并且具有支持传入Callable类型作为参数的构造器
         * 源码：public FutureTask(Callable<V> callable)
         */
        FutureTask<String> futureTask = new FutureTask<>(new MyThread());
        /*
         *不管多少个线程调用futureTask，他们始终是调用的一个futureTask实例
         * 所以控制台只会打印一句 我是一条通过实现Callable接口创建的线程，正在执行run方法，线程名 取决去哪条线程先调用
         */
        new Thread(futureTask, "A线程").start();
        new Thread(futureTask, "B线程").start();
        //get()方法必须放在最后执行，防止主线程阻塞
        System.out.println(futureTask.get());  //获取线程返回值
    }
}

//用实现Callable接口的方式创建线程
class MyThread implements Callable<String> {

    //重写run方法
    @Override
    public String call() throws Exception {
        System.out.println(Thread.currentThread().getName() + "-----我是一条通过实现Callable接口创建的线程，正在执行run方法");
        return "这是真的强大";
    }
}
