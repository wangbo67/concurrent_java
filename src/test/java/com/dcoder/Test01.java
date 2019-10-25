package com.dcoder;


import org.junit.Test;

public class Test01 {
    private long count = 0;

    private void add10k() {
        int idx = 0;
        while (idx++ < 10000) {
            count += 1;
        }
    }

    /**
     * 01-缓存导致的可见性问题
     */
    @Test
    public void testCacheVisibility() {
        Thread th1 = new Thread(() -> {
            add10k();
        });

        Thread th2 = new Thread(() -> {
            add10k();
        });

        th1.start();
        th2.start();

        System.out.println("开始合并");
        try {
            th1.join();
            th2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("完成合并");

        System.out.println("最终结果" + count);
    }

}