package com.tonyxu.bytecode;

/**
 * @author tonyxu
 * @version 1.0
 * @date 2020/10/17 8:54
 */
public class Hello {

    public Hello(){

    }

    public static void main(String[] args){
        double a = 10.0;
        int b = 2 ;
        int c = 3;
        double d[] = new double[2];

        for (int i = 0; i < 2; i++) {
            if(i == 0){
                d[i] = a+b-c;
            }
            if(i == 1){
                d[i] = a/b*c;
            }
        }

        printArray(d);

    }

    private static void printArray(double[] d){
        for (double item:d) {
            System.out.println(item);
        }
    }

}
