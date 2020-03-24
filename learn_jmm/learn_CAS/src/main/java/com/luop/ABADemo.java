package com.luop;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * ABA问题及解决方案
 */
public class ABADemo {

    public static void main(String[] args) {
//        ABAProduce();
        ABASolve();
    }

    //ABA问题的解决
    private static void ABASolve() {
        //定义并初始化一个带版本号的原子引用对象
        AtomicStampedReference<String> stampedReference = new AtomicStampedReference<>("standata", 1);
        new Thread(() -> {
            //模拟完成一个ABA操作
            int stamp = stampedReference.getStamp();
            System.out.println(Thread.currentThread().getName() + "\t当前版本号是：" + stamp + "\t实际版本号是:" + stamp);
            try {
                TimeUnit.SECONDS.sleep(1);   //暂停一秒保证C线程和D线程能拿到相同的版本号
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.print(Thread.currentThread().getName() + "\t交换是否成功："
                    + stampedReference.compareAndSet("standata", "stand", stamp, stamp + 1)
                    + "\t stampedReference的最新值为：" + stampedReference.getReference());
            System.out.println("\t当前版本号是：" + stampedReference.getStamp() + "\t实际版本号是:" + (stamp + 1));
            System.out.print(Thread.currentThread().getName() + "\t交换是否成功："
                    + stampedReference.compareAndSet("stand", "standata", stampedReference.getStamp(), stampedReference.getStamp() + 1)
                    + "\t stampedReference的最新值为：" + stampedReference.getReference());
            System.out.println("\t当前版本号是：" + stampedReference.getStamp() + "\t实际版本号是:" + (stamp + 2));
        }, "C线程").start();
        new Thread(() -> {
            //模拟完成一个ABA操作
            int stamp = stampedReference.getStamp();
            System.out.println(Thread.currentThread().getName() + "\t当前版本号是：" + stamp + "\t实际版本号是:" + stamp);
            try {
                TimeUnit.SECONDS.sleep(3);   //暂停3秒保证C线程完成一次ABA操作
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.print(Thread.currentThread().getName() + "\t交换是否成功："
                    + stampedReference.compareAndSet("standata", "stand", stamp, stamp + 1)
                    + "\t stampedReference的最新值为：" + stampedReference.getReference());
            System.out.println("\t当前版本号是：" + (stamp + 1) + "\t实际版本号是:" + stampedReference.getStamp());
        }, "D线程").start();
    }

    //ABA问题的产生
    private static void ABAProduce() {
        AtomicReference<String> reference = new AtomicReference<>("standata");  //定义并初始化原子引用对象
        new Thread(() -> {
            //模拟完成一个ABA操作
            reference.compareAndSet("standata", "stand");
            reference.compareAndSet("stand", "standata");
        }, "A线程").start();
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);   //保证A线程先执行，并完成一次ABA操作
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("比较交换是否成功：" + reference.compareAndSet("standata", "stand")
                    + "\t reference的最新值为：" + reference.get());
        }, "B线程").start();
    }
}
