package com.dcoder.sync;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @program: concurrent_java
 * @description:
 * @author: wangbo67@github.com
 * @created: 2022-05-22 12:04
 **/
public class WaitNotify {

    private static char currentThreadName = 'A';
    private final Object object = new Object();

    class RunnableA implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 52; i++) {
                synchronized (object) {
                    while (currentThreadName != 'A') {
                        try {
                            object.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    System.out.println(i);
                    if (i % 2 == 0) {
                        currentThreadName = 'B';
                        object.notifyAll();
                    }
                }
            }
        }
    }

    class RunnableB implements Runnable {

        @Override
        public void run() {
            for (char i = 'A'; i < 'Z'; i++) {
                synchronized (object) {
                    while (currentThreadName != 'B') {
                        try {
                            object.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    System.out.println(i);
                    currentThreadName = 'A';
                    object.notifyAll();
                }
            }
        }
    }

    public static void main(String[] args) {
        WaitNotify waitNotify = new WaitNotify();
        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.execute(waitNotify.new RunnableA());
        executor.execute(waitNotify.new RunnableB());

        executor.shutdown();
    }
}
