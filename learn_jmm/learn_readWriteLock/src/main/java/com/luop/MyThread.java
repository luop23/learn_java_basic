package com.luop;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MyThread {
    List<Integer> list = new ArrayList<Integer>();
    ReadWriteLock lock = new ReentrantReadWriteLock();   //创建可重入的读写锁

    public void writeData(Integer index) {
        try {
            lock.writeLock().lock();  //加写锁
            System.out.println("-----开始进行写操作------" + index);
            list.add(index);
            System.out.println("-----写操作完成------" + index);
        } finally {
            lock.writeLock().unlock();  //释放锁
        }
    }

    public void readData(Integer index) {
        try {
            lock.readLock().lock();   //加读锁
            System.out.println("开始进行读操作------" + index);
            System.out.println("读操作完成------" + index);
        } finally {
            lock.readLock().unlock();  //释放锁
        }
    }
}
