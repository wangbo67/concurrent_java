package com.dcoder.future;

import java.util.concurrent.*;

/**
 * @program: concurrent_java
 * @description:
 * @author: wangbo67@github.com
 * @created: 2021-07-03 16:18
 **/
public class NormalFuture {

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Future<Integer> count = executorService.submit(new Counting(5));

        TimeUnit.MILLISECONDS.sleep(500);
        count.cancel(true);
        System.out.println("total:" + count.get());
        System.out.println("main is done");
    }
}


class Counting implements Callable<Integer> {
    private final long period;

    public Counting(long period) {
        this.period = TimeUnit.SECONDS.toNanos(period);
    }

    public Integer call() throws Exception {
        long deadline = System.nanoTime() + period;
        int count = 0;

        for (; ; ) {

            if (deadline <= System.nanoTime()) {
                break;
            }

            System.out.println(++count);

            TimeUnit.MILLISECONDS.sleep(100);
        }
        return count;
    }
}
