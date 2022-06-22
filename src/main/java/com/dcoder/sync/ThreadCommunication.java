package com.dcoder.sync;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.TtlRunnable;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @program: concurrent_java
 * @description: 父子线程通信
 * @author: wangbo67@github.com
 * @created: 2021-06-25 18:52
 **/
public class ThreadCommunication {
    public static void main(String[] args) {
        //latch仅仅为了控制程序运行顺序，与主题无关
        CountDownLatch latch = new CountDownLatch(2);
        //使用TTL提供的TransmittableThreadLocal
        TransmittableThreadLocal<String> local = new TransmittableThreadLocal<>();
        local.set("value:::value-in-parent");
        System.out.println("[1]" + local.get());

        ExecutorService executor = Executors.newFixedThreadPool(1);

        Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
                System.out.println("[2]" + local.get());
                local.set("value:::value-in-runnable1");
                System.out.println("[3]" + local.get());
                latch.countDown();
            }
        };


        Runnable runnable2 = new Runnable() {
            @Override
            public void run() {
                System.out.println("[4]" + local.get());
                local.set("value:::value-in-runnable2");
                System.out.println("[5]" + local.get());
                latch.countDown();
            }
        };
        //使用TTL中的TtlRunnable对JDK原生Runnable进行包装
        Runnable t1 = TtlRunnable.get(runnable1);
        Runnable t2 = TtlRunnable.get(runnable2);

        executor.submit(t1);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        executor.submit(t2);

        try {
            latch.await();
            System.out.println("[6]" + local.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
