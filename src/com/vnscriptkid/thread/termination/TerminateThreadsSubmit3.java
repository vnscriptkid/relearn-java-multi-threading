package com.vnscriptkid.thread.termination;

import java.math.BigInteger;

public class TerminateThreadsSubmit3 {
    public static void main(String[] args) throws InterruptedException {
        var blockingTask = new TerminateThreads2.BlockingTask(new BigInteger("22222"), new BigInteger("10000009999"));

        blockingTask.setDaemon(true);
        blockingTask.start();

        Thread.sleep(2000);
        // todo: interrupt blockingTask thread implicitly when main thread ends
    }

    static class BlockingTask extends Thread {
        BigInteger base;
        BigInteger power;

        public BlockingTask(BigInteger base, BigInteger power) {
            this.base = base;
            this.power = power;
            this.setName(this.getClass().getSimpleName());
        }

        @Override
        public void run() {
            System.out.println(this.base + "^" + this.power + " = " + this.compute());
        }

        private BigInteger compute() {
            BigInteger result = BigInteger.ONE;

            for (BigInteger i = BigInteger.ZERO; i.compareTo(power) != 0; i = i.add(BigInteger.ONE)) {
                if (this.isInterrupted()) {
                    System.out.println(this.getName() + " got received interrupt signal from outside.");
                }
                result = result.multiply(base);
            }

            return result;
        }
    }
}
