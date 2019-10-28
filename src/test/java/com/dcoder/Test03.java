package com.dcoder;

import org.junit.Test;

public class Test03 {
    private int x = 5;

    @Test
    public void testHappensBefore() {
        Thread th1 = new Thread(() -> {
            if(x == 6) {
                System.out.println("线程start()规则：主线程 A 启动子线程 B 后，子线程 B 能够看到主线程在启动子线程 B 前的操作。");
                x = x + 1;
            }
        });

        x = x + 1;
        th1.start();

        try {
            th1.join();
            if(x == 7) {
                System.out.println("线程join()规则：如果在线程 A 中，调用线程 B 的 join() 并成功返回，那么线程 B 中的任意操作 Happens-Before 于该 join() 操作的返回。");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}