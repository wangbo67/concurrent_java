package com.dcoder.sync;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.threadpool.TtlExecutors;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiConsumer;

/**
 * @program: concurrent_java
 * @description:
 * @author: wangbo67@github.com
 * @created: 2021-06-25 18:56
 **/
public class ThreadCommunication2 {
    public static void main(String[] args) {
        //使用TTL提供的TransmittableThreadLocal
        TransmittableThreadLocal<String> local = new TransmittableThreadLocal<>();
        local.set("value:::value-in-parent");
        System.out.println("[1]" + local.get());

        Test test = new Test();
        test.give(local);

        ExecutorService executorService = Executors.newFixedThreadPool(4);
        // 额外的处理，生成修饰了的对象executorService
        executorService = TtlExecutors.getTtlExecutorService(executorService);
        CompletableFuture completableFuture = CompletableFuture.runAsync(() -> {
            System.out.println("[2]" + local.get());
            local.set("value:::value-in-runnable1");
            System.out.println("[3]" + local.get());
            //test.give(local);
            //test.print();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("[4]" + local.get());
        }, executorService);

        completableFuture.whenComplete((o, o2) -> {
            System.out.println("[5]" + local.get());
        });

        local.set("value:::value-in-parent-reset");
        System.out.println("[6]" + local.get());
    }
}
