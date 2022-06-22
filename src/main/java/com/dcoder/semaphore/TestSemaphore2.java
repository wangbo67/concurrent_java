package com.dcoder.semaphore;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @program: concurrent_java
 * @description:
 * @author: wangbo67@github.com
 * @created: 2021-09-13 10:27
 **/
public class TestSemaphore2 {
    private static int defaultSize = 2;

    public static void main(String[] args) throws InterruptedException {
        final Semaphore semaphore = new Semaphore(defaultSize);
        System.out.println("availablePermits:" + semaphore.availablePermits() + ",semaphore.tryAcquire(3,1, TimeUnit.SECONDS):" + semaphore.tryAcquire(3, 1, TimeUnit.SECONDS));
        semaphore.release();
        System.out.println("availablePermits:" + semaphore.availablePermits() + ",semaphore.tryAcquire(3,1, TimeUnit.SECONDS):" + semaphore.tryAcquire(3, 1, TimeUnit.SECONDS));
    }
}
