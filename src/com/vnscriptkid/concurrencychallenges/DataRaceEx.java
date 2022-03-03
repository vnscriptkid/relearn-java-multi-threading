package com.vnscriptkid.concurrencychallenges;

public class DataRaceEx {
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
        int x = 0;
        int y = 0;

        void increment() {
            x++;
            y++;
        }

        public void checkForDataRace() {
            if (y > x) {
                // todo: why this should not happen
                // todo: why it happens
                System.out.println("Apparently, this should not happen.");
            }
        }
    }
}
