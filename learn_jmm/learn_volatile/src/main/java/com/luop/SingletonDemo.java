package com.luop;

/**
 * @Author: luoping
 * @Date: 2020/3/24 13:47
 * @Description:
 */

/**
 * 单例模式
 */
public class SingletonDemo {

    public static void main(String[] args) {
//        demo1();
        demo2();
    }

    private static void demo2() {
        //多线程下的单例模式
        for (int i = 0; i < 10; i++) {
            new Thread(Singleton2::getInstance).start();
        }
    }

    private static void demo1() {
        //单线程下的单例模式_
        Singleton.getSingleton();
    }
}

class Singleton2 {
    /**
     * volatile的目的：防止指令重排
     * 指令重排后，有可能存在一种情况：singleton对象被分配了内存空间，但是还没有被初始化。这样就会导致某些线程会返回空值对象
     */
    private static volatile Singleton2 singleton = null;
    private Singleton2(){
        System.out.println(Thread.currentThread().getName() + "线程\t创建单例对象");
    }

    //DCL(Double Check Lock   双重检查锁)
    //双重检查锁机制不一定线程安全，原因是有指令重排的存在。
    public static Singleton2 getInstance(){
        if (singleton ==null){
            synchronized (Singleton2.class){
                if (singleton == null){
                    singleton = new Singleton2();
                }
            }
        }
        return singleton;
    }
}

class Singleton {
    private static Singleton singleton = null;

    private Singleton() {
        System.out.println(Thread.currentThread().getName() + "线程\t创建单例对象");
    }

    public static Singleton getSingleton() {
        if (singleton == null) {
            singleton = new Singleton();
        }
        return singleton;
    }
}
