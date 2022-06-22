package com.dcoder;

import java.util.ArrayList;
import java.util.List;

/**
 * "等待-通知"机制优化循环等待
 */
public class Test0601 {
    private List<Object> als = new ArrayList<>();

    class Allocator {
        //一次性申请所有资源
        synchronized void apply(Object from, Object to) {
            //经典写法
            while (als.contains(from) || als.contains(to)) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            als.add(from);
            als.add(to);
        }

        synchronized void free(Object from, Object to) {
            als.remove(from);
            als.remove(to);

            notifyAll();
        }
    }

    class Account {
        //als 必须为单例
        private Allocator als;
        private int balance;

        //转账
        void transfer(Account target, int amt) {
            try {
                als.apply(this, target);
                //锁定转出账户
                synchronized (this) {
                    //锁定转入账户
                    synchronized (target) {
                        if(this.balance > amt) {
                            this.balance -= amt;
                            target.balance += amt;
                        }
                    }
                }
            } finally {
                als.free(this, target);
            }
        }
    }
}
