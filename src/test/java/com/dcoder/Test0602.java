package com.dcoder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Test0602 {
    // 测试转账的main方法
    public static void main(String[] args) throws InterruptedException {
        Account src = new Account(10000);
        Account target = new Account(10000);
        CountDownLatch countDownLatch = new CountDownLatch(9999);
        for (int i = 0; i < 9999; i++) {
            new Thread(() -> {
                src.transactionToTarget(1, target);
                countDownLatch.countDown();
            }).start();
        }
        countDownLatch.await();
        System.out.println("src=" + src.getBalance());
        System.out.println("target=" + target.getBalance());
    }

    //账户类
    static class Account {
        public Account(Integer balance) {
            this.balance = balance;
        }

        private Integer balance;

        //转账方法
        public void transactionToTarget(Integer money, Account target) {
            Allocator.getInstance().apply(this, target);
            this.balance -= money;
            target.setBalance(target.getBalance() + money);
            Allocator.getInstance().release(this, target);
        }

        public Integer getBalance() {
            return balance;
        }

        public void setBalance(Integer balance) {
            this.balance = balance;
        }
    }

    //单例锁类
    static class Allocator {
        private Allocator() {
        }

        private List<Account> locks = new ArrayList<>();

        public synchronized void apply(Account src, Account tag) {
            while (locks.contains(src) || locks.contains(tag)) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                }
            }
            locks.add(src);
            locks.add(tag);
        }

        public synchronized void release(Account src, Account tag) {
            locks.remove(src);
            locks.remove(tag);
            this.notifyAll();
        }

        public static Allocator getInstance() {
            return AllocatorSingle.install;
        }

        static class AllocatorSingle {
            public static Allocator install = new Allocator();
        }
    }
}
