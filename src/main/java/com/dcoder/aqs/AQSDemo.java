package com.dcoder.aqs;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @program: concurrent_java
 * @description:
 * @author: wangbo67@github.com
 * @created: 2021-09-06 10:45
 **/
public class AQSDemo {
    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        MyThread t1 = new MyThread("t1", lock);
        MyThread t2 = new MyThread("t2", lock);
        t1.start();
        t2.start();
    }
}
