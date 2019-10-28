package com.dcoder;

import org.junit.Test;

public class Test02 {
    volatile boolean v = false;
    int x = 0;

    public void writer() {
        v = true;
        x = 10;
    }

    public void reader() {
        if(v == true) {
            System.out.println(x);
        }
    }

    @Test
    public void testVolatile() {
        writer();
        reader();
    }
}
