package com.vnscriptkid.thread.join;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JoinThreadsSubmit1 {
    public static void main(String[] args) throws InterruptedException {
        List<Long> inputNumbers = Arrays.asList(0L, 3457L, 35599999L, 2267L, 4651L, 23L, 5551L);

        List<FactorialThread> threads = new ArrayList<>();

        // todo: initialize each factorial thread, add to threads list
        for (long inputNumber : inputNumbers) {
            threads.add(new FactorialThread(inputNumber));
        }

        // todo: start each factorial thread
        for (Thread thread : threads) {
            thread.setDaemon(true);
            thread.start();
        }

        // todo: give computation time of 2 secs for threads before printing result
        for (Thread thread: threads) {
            thread.join(2000);
        }

        System.out.println("Back to main thread");

        // print result if finished
        for (FactorialThread thread : threads) {
            System.out.println(thread);
        }

        // todo: i want to stop program when main thread reaches the end, even work threads are still running
    }

    static class FactorialThread extends Thread {
        private long number;
        private BigInteger result;
        private boolean isFinished = false;

        public FactorialThread(long number) {
            this.setName(this.getClass().getSimpleName()  + "-" + this.number);
            this.number = number;
        }

        @Override
        public void run() {
            // todo: calculate factorial, store in result, flip isFinished
            this.result = this.calculate();
            this.isFinished = true;
        }

        private BigInteger calculate() {
            BigInteger temp = BigInteger.ONE;

            for (long i = 1L; i <= number; i++) {
                temp = temp.multiply(new BigInteger(Long.toString(i)));
            }

            return temp;
        }

        @Override
        public String toString() {
            // todo: if isFinished, print result, else says: is in-progress
            if (this.isFinished)
                return this.number + "! = " + this.result;

            return this.number + "! ... in-progress ...";
        }
    }
}
