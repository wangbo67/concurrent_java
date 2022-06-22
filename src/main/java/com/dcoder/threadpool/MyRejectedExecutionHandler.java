package com.dcoder.threadpool;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @program: concurrent_java
 * @description: 任务拒绝策略
 * @author: wangbo67@github.com
 * @created: 2021-05-18 23:42
 **/
public class MyRejectedExecutionHandler implements RejectedExecutionHandler {
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        System.out.println(r.toString() + " is rejected");
    }
}
