package com.luop;

import java.util.concurrent.TimeUnit;

/**
 * 死锁
 */
public class DeadLockDemo {

    public static void main(String[] args) {

        String lock_A = "lock_AA";
        String lock_B = "lock_BB";

        new Thread(new HoldLockThread(lock_A, lock_B), "AAA").start();

        new Thread(new HoldLockThread(lock_B, lock_A), "BBB").start();
    }
}

class HoldLockThread implements Runnable {

    private String lock1;

    private String lock2;

    public HoldLockThread(String lock1, String lock2) {
        this.lock1 = lock1;
        this.lock2 = lock2;
    }

    @Override
    public void run() {
        synchronized (lock1) {
            System.out.println(Thread.currentThread().getName() + "当前持有锁：" + lock1 + "\t准备持有锁：" + lock2);
            try {
                TimeUnit.SECONDS.sleep(2);   //保证A、B两线程都能进入同步块
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lock2) {
                System.out.println(Thread.currentThread().getName() + "当前持有锁：" + lock2 + "\t准备持有锁：" + lock1);
            }
        }
    }

}
