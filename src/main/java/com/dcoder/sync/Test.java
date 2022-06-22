package com.dcoder.sync;

import com.alibaba.ttl.TransmittableThreadLocal;

/**
 * @program: concurrent_java
 * @description:
 * @author: wangbo67@github.com
 * @created: 2021-06-26 14:33
 **/
public class Test {

    TransmittableThreadLocal<String> local = new TransmittableThreadLocal<>();

    public void print() {
        System.out.println("[test]" + local.get());
    }

    public void give(TransmittableThreadLocal<String> local) {
        this.local = local;
    }
}
