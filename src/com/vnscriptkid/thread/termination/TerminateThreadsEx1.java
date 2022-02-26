package com.vnscriptkid.thread.termination;

public class TerminateThreadsEx1 {
    public static void main(String[] args) throws InterruptedException {
        var sleepingThread = new SleepingThread();

        // todo: let sleepingThread sleeps

        // todo: from main thread, after sleeping thread has slept more than 2 secs, then interrupt
    }

    // todo: make this runs on one thread
    static class SleepingThread {
        public SleepingThread() {
            // todo: set thread name to current class name
        }

        // todo: when it runs, sleep for 50 secs
        // todo: handle interrupt signal from outside
    }
}
