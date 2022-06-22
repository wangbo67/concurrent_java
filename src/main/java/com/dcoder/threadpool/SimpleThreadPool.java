package com.dcoder.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @program: concurrent_java
 * @description: 简单线程池示例
 * @author: wangbo67@github.com
 * @created: 2021-05-18 23:31
 **/
public class SimpleThreadPool {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 10; i++) {
            Runnable worker = new WorkThread("" + i);
            executor.submit(worker);
        }
        executor.shutdown();

        while (!executor.isTerminated()) { // Wait until all threads are finish,and also you can use "executor.awaitTermination();" to wait
        }
        System.out.println("Finished all threads");
    }
}
