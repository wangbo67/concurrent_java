package com.dcoder.join;

/**
 * @program: concurrent_java
 * @description:
 * @author: wangbo67@github.com
 * @created: 2021-09-01 16:18
 **/
public class JoinTest {

    static class Run implements Runnable {

        @Override
        public void run() {
            try {
                System.out.println("thread sleep...");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Thread thread = new Thread(new Run());
        thread.start();
        System.out.println(111);
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(222);
    }
}
