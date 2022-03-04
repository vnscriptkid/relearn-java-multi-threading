package com.vnscriptkid.concurrencychallenges;

public class DataRaceSubmit {
    public static void main(String[] args) {
        SharedClass sharedClass = new SharedClass();

        // one thread trying to modify shared resource
        Thread thread1 = new Thread(() -> {
            while (true) {
                sharedClass.increment();
            }
        });

        // one thread trying to read from shared resource
        Thread thread2 = new Thread(() -> {
            while (true) {
                sharedClass.checkForDataRace();
            }
        });

        thread1.start();
        thread2.start();
    }

    public static class SharedClass {
        // todo: try 2 ways to fix problem, which on is better, why?
        // solution: try to make sure ops in increment() are executed in-order.
        // 1) synchronized: prevent concurrent execution
        // 2) volatile
        volatile int x = 0;
        volatile int y = 0;

        void increment() {
            x++;
            y++;
            // these 2 ops, are independent. so cpu could potentially execute either one first to optimize
            // above could become:
            // y++
            // x++
        }

        public void checkForDataRace() {
            System.out.println("checking");
            if (y > x) {
                // todo: why this should not happen
                // todo: why it happens
                throw new RuntimeException("this should not happen.");
            }
        }
    }
}
