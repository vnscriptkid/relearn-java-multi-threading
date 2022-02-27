package com.vnscriptkid.thread.termination;

public class TerminateThreadsSubmit1 {
    public static void main(String[] args) throws InterruptedException {
        var sleepingThread = new SleepingThread();

        // todo: let sleepingThread sleeps
        sleepingThread.start();

        // todo: from main thread, after sleeping thread has slept more than 2 secs, then interrupt
        Thread.sleep(2000);

        sleepingThread.interrupt();
    }

    // todo: make this runs on one thread
    static class SleepingThread extends Thread {
        public SleepingThread() {
            // todo: set thread name to current class name
            this.setName(this.getClass().getSimpleName());
        }

        @Override
        public void run() {
            try {
                Thread.sleep(50000);
            } catch (InterruptedException e) {
                System.out.println(this.getName() + " has been woken up from outside.");
            }
        }

        // todo: when it runs, sleep for 50 secs
        // todo: handle interrupt signal from outside
    }
}
