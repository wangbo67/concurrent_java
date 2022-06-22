package com.dcoder;

import org.junit.Test;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.Semaphore;
import java.util.function.Function;

public class Test16 {
    class ObjPool<T, R> {
        final List<T> pool;
        // 用信号量实现限流器
        final Semaphore sem;

        // 构造函数
        ObjPool(int size, T t) {
            pool = new Vector<T>() {
            };
            for (int i = 0; i < size; i++) {
                pool.add(t);
            }
            sem = new Semaphore(size);
        }

        // 利用对象池的对象，调用func
        R exec(Function<T, R> func) throws Exception {
            T t = null;
            sem.acquire();
            try {
                t = pool.remove(0);
                return func.apply(t);
            } finally {
                pool.add(t);
                sem.release();
            }
        }
    }

    @Test
    public void test() {
        // 创建对象池
        ObjPool<Integer, Object> pool = new ObjPool<Integer, Object>(10, 2);
        // 通过对象池获取t，之后执行
        try {
            pool.exec(t ->
            {
                System.out.println(t);
                return t.toString();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
