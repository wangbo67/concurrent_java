package com.dcoder.sync;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @program: concurrent_java
 * @description:
 * @author: wangbo67@github.com
 * @created: 2022-05-22 13:01
 **/
public class LockCondition {
    private Lock lock = new ReentrantLock();
    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();

    private static char currentThreadName = 'A';

    class RunnableA implements Runnable {
        @Override
        public void run() {
            for (int i = 1; i <= 52; i++) {
                lock.lock();
                while (currentThreadName != 'A') {
                    try {
                        condition1.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlock();
                    }
                }

                System.out.println(i);
                if(i % 2 == 0) {
                    currentThreadName = 'B';
                    condition2.signalAll();
                }
            }
        }
    }

    class RunnableB implements Runnable {

        @Override
        public void run() {
            for (char i = 'A'; i <= 'Z'; i++) {
                lock.lock();
                while (currentThreadName != 'B') {
                    try {
                        condition2.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlock();
                    }
                }

                System.out.println(i);
                currentThreadName = 'A';
                condition1.signalAll();
            }
        }
    }

    public static void main(String[] args) {
        LockCondition lockCondition = new LockCondition();
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.execute(lockCondition.new RunnableA());
        executorService.execute(lockCondition.new RunnableB());

        executorService.shutdown();
    }
}
