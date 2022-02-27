package com.vnscriptkid.thread.termination;

import java.math.BigInteger;

public class TerminateThreadsSubmit2 {
    public static void main(String[] args) throws InterruptedException {
        var blockingTask = new BlockingTask(new BigInteger("22222"), new BigInteger("1000000"));

        blockingTask.start();

        // todo: if calculating has been for more than 2 secs, stop it
        Thread.sleep(2000);
        System.out.println("after 2 secs...");

        blockingTask.interrupt();
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
            // todo: loop power times to calculate base^power
            // todo: it could be running in long time, so for each iteration, check interrupt signal and return 0
            BigInteger result = BigInteger.ONE;

            for (BigInteger i = BigInteger.ZERO; i.compareTo(power) != 0; i = i.add(BigInteger.ONE)) {
                if (this.isInterrupted()) {
                    System.out.println(this.getName() + " got interrupted from outside.");
                    return BigInteger.ZERO;
                }
                result = result.multiply(power);
            }

            return result;
        }
    }
}
