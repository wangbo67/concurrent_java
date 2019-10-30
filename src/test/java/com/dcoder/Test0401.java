package com.dcoder;

import org.junit.Test;

/**
 * 互斥锁：解决原子性问题
 */
public class Test0401 {
    long value = 0;

    public synchronized long getValue() {
        return value;
    }

    public synchronized void addValue() {
        int idx = 0;
        while (idx++ < 10000) {
            value += 1;
        }
    }

    @Test
    public void testSynchronized() {
        Thread addThread1 = new Thread(() -> {
            addValue();
        });

        Thread addThread2 = new Thread(() -> {
            addValue();
        });

        Thread getThread = new Thread(() -> {
            System.out.println(getValue());
        });

        addThread1.start();
        addThread2.start();

        try {
            addThread1.join();
            addThread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        getThread.start();
    }
}
