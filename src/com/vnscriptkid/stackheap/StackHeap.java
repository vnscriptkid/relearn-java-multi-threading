package com.vnscriptkid.stackheap;

public class StackHeap {
    public static void main(String[] args) {
        int a = 10;
        int b = 20;
        int result = sum(a, b);
        System.out.println(result);
        // todo: run with debugger to see stack, local variables, arguments...
    }

    private static int sum(int a, int b) {
        return a + b;
    }
}
