package com.tonyxu.thread.util;

/**
 * Created on 2020/11/10.
 *
 * @author <a href="191284969@qq.com">Tony xu</a>
 */
public class FiboUtil {

    public static int sum() {
        return fiboNew(36);
    }

    private static int fibo(int a) {
        if ( a < 2) {
            return 1;
        }
        return fibo(a-1) + fibo(a-2);
    }

    private static int fiboNew(int a) {

        if ( a < 2){
            return 1;
        }

        int a1 = 0, a2 = 1;

        for (int i = 2;i <= a;i++) {
            a2 = a1 + (a1 = a2);
        }

        return a1 + a2;
    }


}
