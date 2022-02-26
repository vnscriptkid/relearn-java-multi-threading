package com.vnscriptkid.thread.join;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JoinThreadsEx1 {
    public static void main(String[] args) throws InterruptedException {
        List<Long> inputNumbers = Arrays.asList(0L, 3457L, 355999L, 2267L, 4651L, 23L, 5551L);

        List<JoinThreads.FactorialThread> threads = new ArrayList<>();

        // todo: initialize each factorial thread, add to threads list

        // todo: start each factorial thread

        // todo: give computation time of 2 secs for threads before printing result

        System.out.println("Back to main thread");

        // print result if finished
        for (JoinThreads.FactorialThread thread : threads) {
            System.out.println(thread);
        }

        // todo: i want to stop program when main thread reaches the end, even work threads are still running
    }

    static class FactorialThread extends Thread {
        private long number;
        private BigInteger result;
        private boolean isFinished = false;

        public FactorialThread(long number) {
            this.number = number;
        }

        @Override
        public void run() {
            // todo: calculate factorial, store in result, flip isFinished
        }

        @Override
        public String toString() {
            // todo: if isFinished, print result, else says: is in-progress
            return "";
        }
    }
}
