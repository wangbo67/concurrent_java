package com.dcoder;

/**
 * 保护没有关联的多个资源
 */
public class Test0402 {

    /**
     * 同一把锁(this)锁定多个资源
     */
    class Account1 {
        /**
         * 账户余额
         */
        private Integer balance;
        /**
         * 账户密码
         */
        private String password;

        /**
         * 取款
         * @param amt
         */
        synchronized void withdraw(Integer amt) {
            if (balance > amt) {
                balance -= amt;
            }
        }

        /**
         * 查询余额
         * @return
         */
        synchronized Integer getBalance() {
            return balance;
        }

        /**
         * 修改密码
         * @param newPw
         */
        synchronized void updatePassword(String newPw) {
            this.password = newPw;
        }

        /**
         * 查询密码
         * @return
         */
        synchronized String getPassword() {
            return this.password;
        }
    }

    /**
     * 不同的锁分别锁定资源：细粒度互斥锁，提升性能
     */
    class Account2 {
        /**
         * 账户余额
         */
        private Integer balance;
        /**
         * 账户余额锁
         */
        private final Object balLock = new Object();
        /**
         * 账户密码
         */
        private String password;
        /**
         * 账户密码锁
         */
        private final Object pswLock = new Object();

        /**
         * 取款
         * @param amt
         */
        void withdraw(Integer amt) {
            synchronized (balLock) {
                if (balance > amt) {
                    balance -= amt;
                }
            }
        }

        /**
         * 查询余额
         * @return
         */
        Integer getBalance() {
            synchronized (balLock) {
                return balance;
            }
        }

        /**
         * 修改密码
         * @param newPw
         */
        void updatePassword(String newPw) {
            synchronized (pswLock) {
                this.password = newPw;
            }
        }

        /**
         * 查询密码
         * @return
         */
        String getPassword() {
            synchronized (pswLock) {
                return this.password;
            }
        }
    }

    /**
     * 同一个类作为锁，覆盖所有相关的资源
     */
    class Account {
        private int balance;

        /**
         * 转账
         * @param target
         * @param amt
         */
        void transfer(Account target, int amt) {
            synchronized (Account.class) {
                if(this.balance > amt) {
                    this.balance -= amt;
                    target.balance += amt;
                }
            }
        }
    }
}
