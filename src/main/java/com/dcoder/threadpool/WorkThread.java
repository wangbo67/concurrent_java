package com.dcoder.threadpool;

/**
 * @program: concurrent_java
 * @description: 工作线程
 * @author: wangbo67@github.com
 * @created: 2021-05-18 23:27
 **/
public class WorkThread implements Runnable {

    private String command;

    public WorkThread(String command) {
        this.command = command;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "starts. Command is " + command);
        processCommand();
        System.out.println(Thread.currentThread().getName() + "end.");
    }

    private void processCommand() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return this.command;
    }
}
