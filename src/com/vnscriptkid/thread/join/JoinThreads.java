package com.vnscriptkid.thread.join;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JoinThreads {
    public static void main(String[] args) throws InterruptedException {
        List<Long> inputNumbers = Arrays.asList(0L, 3457L, 355999L, 2267L, 4651L, 23L, 5551L);

        List<FactorialThread> threads = new ArrayList<>();

        for (long inputNumber : inputNumbers) {
            threads.add(new FactorialThread(inputNumber));
        }

        for (FactorialThread thread : threads) {
            thread.setDaemon(true);
            thread.start();
        }

        for (FactorialThread thread : threads) {
            thread.join(2000);
        }

        System.out.println("Back to main thread");

        // todo: give computation time for threads before printing result
        // main thread must somehow wait for worker threads

        // print result if finished
        for (FactorialThread thread : threads) {
            System.out.println(thread);
        }
    }

    static class FactorialThread extends Thread {
        private long number;
        private BigInteger result;
        private boolean isFinished = false;
        
        public FactorialThread(long number) {
            this.number = number;
            this.setName(this.getClass().getSimpleName() + this.number);
        }

        @Override
        public void run() {
            this.result = this.calculate();
            this.isFinished = true;
        }

        public BigInteger calculate() {
            BigInteger result = BigInteger.ONE;

            for (long i = 1L; i <= this.number; i++) {
                result = result.multiply(new BigInteger(Long.toString(i)));
            }

            return result;
        }

        @Override
        public String toString() {
            if (this.isFinished)
                return this.number  + "! = " + this.result;
            else
                return "calculating " + this.number + "! is in-progress...";
        }
    }
}
