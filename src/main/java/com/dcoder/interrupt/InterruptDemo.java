package com.dcoder.interrupt;

/**
 * @program: concurrent_java
 * @description: 线程中断异常 demo
 * @author: wangbo67@github.com
 * @created: 2021-05-07 17:16
 **/
public class InterruptDemo {
    public static void interrupt() {
        Thread thread = Thread.currentThread();
        while (true) {
            if(thread.isInterrupted()) {
                break;
            }
        }

        System.out.println(thread.isInterrupted());

        try {
            thread.sleep(1000);
        } catch (InterruptedException e) {
            thread.interrupt();
            e.printStackTrace();
        }
    }
}
