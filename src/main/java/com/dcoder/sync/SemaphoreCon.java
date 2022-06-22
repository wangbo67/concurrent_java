package com.dcoder.sync;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @program: concurrent_java
 * @description:
 * @author: wangbo67@github.com
 * @created: 2022-05-22 15:07
 **/
public class SemaphoreCon {
    public final Semaphore semaphore = new Semaphore(1);
    private static char currentThreadName = 'A';

    class RunnableA implements Runnable {

        @Override
        public void run() {
            for (int i = 1; i <= 52; i++) {
                try {
                    while (currentThreadName != 'A') {
                        semaphore.release();
                    }
                    semaphore.acquire();
                    System.out.println(i);
                    if (i % 2 == 0) {
                        currentThreadName = 'B';
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                }
            }
        }
    }

    class RunnableB implements Runnable {

        @Override
        public void run() {
            for (char i = 'A'; i <= 'Z'; i++) {
                try {
                    while (currentThreadName != 'B') {
                        semaphore.release();
                    }
                    semaphore.acquire();
                    System.out.println(i);
                    currentThreadName = 'A';
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                }
            }
        }
    }

    public static void main(String[] args) {
        SemaphoreCon semaphoreCon = new SemaphoreCon();
        ExecutorService service = Executors.newFixedThreadPool(2);
        service.execute(semaphoreCon.new RunnableA());
        service.execute(semaphoreCon.new RunnableB());

        service.shutdown();
    }
}
