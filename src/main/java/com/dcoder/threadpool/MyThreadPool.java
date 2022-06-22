package com.dcoder.threadpool;

import java.util.concurrent.*;

/**
 * @program: concurrent_java
 * @description:
 * @author: wangbo67@github.com
 * @created: 2021-05-18 23:46
 **/
public class MyThreadPool {
    public static void main(String[] args) throws InterruptedException {
        MyRejectedExecutionHandler rejectedExecutionHandler = new MyRejectedExecutionHandler();
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2,
                4,
                10,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(2),
                threadFactory,
                rejectedExecutionHandler);

        MyMonitorThread monitorThread = new MyMonitorThread(executor, 3);
        new Thread(monitorThread).start();
        for (int i = 0; i < 10; i++) {
            executor.execute(new WorkThread("cmd" + i));
        }
        Thread.sleep(3000);
        executor.shutdown();
        monitorThread.shutdown();
    }
}
