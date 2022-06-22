package com.dcoder;

import org.junit.Test;

public class Test0901 {

    @Test
    public void test() {
        Thread cTh = Thread.currentThread();
        System.out.println(cTh);
        while (true) {
            if (cTh.isInterrupted()) {
                break;
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // 在触发 InterruptedException 异常的同时，JVM 会同时把线程的中断标志位清除，所以这个时候th.isInterrupted()返回的是 false。
                // 正确的处理方式应该是捕获异常之后重新设置中断标志位。
                cTh.interrupt();
                e.printStackTrace();
            }
        }

    }

}
