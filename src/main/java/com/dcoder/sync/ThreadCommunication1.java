package com.dcoder.sync;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.threadpool.TtlExecutors;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @program: concurrent_java
 * @description:
 * @author: wangbo67@github.com
 * @created: 2021-06-25 18:56
 **/
public class ThreadCommunication1 {
    public static void main(String[] args) {
        //latch仅仅为了控制程序运行顺序，与主题无关
        CountDownLatch latch = new CountDownLatch(2);
        //使用TTL提供的TransmittableThreadLocal
        TransmittableThreadLocal<String> local = new TransmittableThreadLocal<>();
        local.set("value:::value-in-parent");
        System.out.println("[1]" + local.get());

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        // 额外的处理，生成修饰了的对象executorService
        executorService = TtlExecutors.getTtlExecutorService(executorService);

        Runnable runnable1 = () -> {
            System.out.println("[2]" + local.get());
            local.set("value:::value-in-runnable1");
            System.out.println("[3]" + local.get());
            latch.countDown();
        };

        Runnable runnable2 = () -> {
            System.out.println("[4]" + local.get());
            local.set("value:::value-in-runnable2");
            System.out.println("[5]" + local.get());
            latch.countDown();
        };

        executorService.submit(runnable1);
        try {
            Thread.sleep(3000);
            local.set("value:::value-in-parent-reset");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executorService.submit(runnable2);

        try {
            latch.await();
            System.out.println("[6]" + local.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
