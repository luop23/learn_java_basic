package com.luop;

/**
 * 五个线程同时进行读操作，五个操作同时进行写操作
 */
public class ReadWriteLockDemo {

    public static void main(String[] args) {
        final MyThread myThread = new MyThread();
        for (int i = 0; i < 5; i++) {
            final int temp = i;
            new Thread(() -> myThread.writeData(temp)).start();
        }

        for (int i = 0; i < 5; i++) {
            final int temp = i;
            new Thread(() -> myThread.readData(temp)).start();
        }
    }
}


