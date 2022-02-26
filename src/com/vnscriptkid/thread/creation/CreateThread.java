package com.vnscriptkid.thread.creation;

class CreateThread {
    public static void main(String[] args) throws InterruptedException {
//        VERBOSE WAY
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("We are now in thread " + Thread.currentThread().getName());
//            }
//        });

        var betterThread = new BetterThread();
        betterThread.run();

        Thread thread = new Thread(() -> {
            System.out.println("We are now in thread: " + Thread.currentThread().getName());
            System.out.println("Thread has priority of " + Thread.currentThread().getPriority());
            throw new RuntimeException("serious thing happened.");
        });

        thread.setName("my thread");
        thread.setPriority(Thread.MAX_PRIORITY);

        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println("Handling err from thread " + t.getName() + ". Err is " + e.getMessage());
            }
        });

        System.out.println("We are in thread " + Thread.currentThread().getName() + " before starting thread.");
        // this takes some time
        thread.start();
        System.out.println("We are in thread " + Thread.currentThread().getName() + " after starting thread.");

//        System.out.println("before sleep");
//        Thread.sleep(3000);
//        System.out.println("after sleep");
    }

    static class BetterThread extends Thread {
        @Override
        public void run() {
            System.out.println("Hello from thread: " + this.getName());
        }
    }
}