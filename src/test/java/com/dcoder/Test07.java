package com.dcoder;

import org.junit.Test;

import java.util.Random;
import java.util.Vector;

public class Test07 {
    private void addIfNotExist(Vector v, Object o) {
        if (!v.contains(o)) {
            v.add(o);
        }
    }

    @Test
    public void test() {
        Vector v = new Vector();
        Integer val = new Random().nextInt();
        addIfNotExist(v, val);

        System.out.println(v);
    }
}
