package com.dcoder.aqs;

import java.util.concurrent.locks.Lock;

/**
 * @program: concurrent_java
 * @description:
 * @author: wangbo67@github.com
 * @created: 2021-09-06 10:46
 **/
class MyThread extends Thread {
    private Lock lock;

    public MyThread(String name, Lock lock) {
        super(name);
        this.lock = lock;
    }

    public void run() {
        System.out.println(Thread.currentThread() + " lock");
        lock.lock();
        try {
            System.out.println(Thread.currentThread() + " running");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            System.out.println(Thread.currentThread() + " unlock");
        }
    }
}
