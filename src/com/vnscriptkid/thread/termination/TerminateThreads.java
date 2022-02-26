package com.vnscriptkid.thread.termination;

public class TerminateThreads {
    public static void main(String[] args) throws InterruptedException {
        var sleepingThread = new SleepingThread();

        sleepingThread.start();

        Thread.sleep(2000);
        sleepingThread.interrupt();
    }

    static class SleepingThread extends Thread {
        public SleepingThread() {
            this.setName(this.getClass().getSimpleName());
        }
        @Override
        public void run() {
            try {
                Thread.sleep(50000);
            } catch (InterruptedException e) {
                System.out.println(this.getName() + " received interrupt signal from outside.");
            }
        }
    }
}
