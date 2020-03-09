package com.luop;

import com.google.common.collect.Lists;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class DemoList {
    /**
     * 测试多线程下List线程安全的几种写法
     * @param args
     */

    public static void main(String[] args) {
        notSafeList();
//        safeVector();
//        safe2();
//        safe3();
    }

    private static void safe3() {
        List<String> list = new CopyOnWriteArrayList<>();
        for (int i = 1; i <= 30; i++) {
            new Thread(() ->{
                list.add(UUID.randomUUID().toString().substring(0,5));
                System.out.println(list);
            }).start();
        }
    }

    //Collections.synchronizedList();  这种方式处理小数据量还行
    private static void safe2() {
        List<String> list = Collections.synchronizedList(new ArrayList<>());
        for (int i = 1; i <= 30; i++) {
            new Thread(() ->{
                list.add(UUID.randomUUID().toString().substring(0,5));
                System.out.println(list);
            }).start();
        }
    }

    //vector是线程安全
    private static void safeVector() {
        List<String> vector = new Vector<>();
        for (int i = 1; i <= 30; i++) {
            new Thread(() ->{
                vector.add(UUID.randomUUID().toString().substring(0,5));
                System.out.println(vector);
            }).start();
        }
    }

    //ArrayList在多线程下是非线程安全的
    private static void notSafeList() {
        List<String> list = Lists.newArrayList();
        for (int i = 1; i <= 5; i++) {
            new Thread(() ->{
                list.add(UUID.randomUUID().toString().substring(0,5));
                System.out.println(list);
            }).start();
        }
    }
}
