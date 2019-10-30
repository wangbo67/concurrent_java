package com.dcoder;

public class Test0503 {
    /**
     * 破坏循环等待条件
     */
    class Account {
        private int id;
        private int balance;

        void transfer(Account target, int amt) {
            Account smaller = this;
            Account bigger = target;
            if(this.id > target.id) {
                smaller = target;
                bigger = this;

                //锁定序号小的账户
                synchronized (smaller) {
                    //锁定序号大的账户
                    synchronized (bigger) {
                        if(this.balance > amt) {
                            this.balance -= amt;
                            target.balance += amt;
                        }
                    }
                }
            }
        }
    }
}
