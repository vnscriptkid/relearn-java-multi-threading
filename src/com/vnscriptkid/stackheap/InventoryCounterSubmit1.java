package com.vnscriptkid.stackheap;

public class InventoryCounterSubmit1 {
    public static void main(String[] args) throws InterruptedException {
        var inventoryCounter = new InventoryCounter();

        // todo: create 2 threads
        IncrementingThread incrementingThread = new IncrementingThread(inventoryCounter, 10000);
        DecrementingThread decrementingThread = new DecrementingThread(inventoryCounter, 10000);

        // todo: make them run sequentially, see result
        incrementingThread.start();
        decrementingThread.start();

        incrementingThread.join();
        decrementingThread.join();
        // todo: then make them run concurrently

        // todo: what is the cause of issue?

        // todo: make sure this line is executed when both threads are done
        System.out.println("Value now: " + inventoryCounter.getItems());
    }

    // todo: make this runs on one thread, works on one inventoryCounter object
    static class IncrementingThread extends Thread {
        private InventoryCounter inventoryCounter;
        private int runningTimes;

        public IncrementingThread(InventoryCounter inventoryCounter, int runningTimes) {
            this.inventoryCounter = inventoryCounter;
            this.runningTimes = runningTimes;
        }
        // todo: increment inventoryCounter times times while running

        @Override
        public void run() {
            for (int i = 0; i < this.runningTimes; i++) {
                this.inventoryCounter.increment();
            }
        }
    }

    // todo: make DecrementingThread similar to IncrementingThread
    static class DecrementingThread extends Thread {
        private InventoryCounter inventoryCounter;
        private int runningTimes;

        public DecrementingThread(InventoryCounter inventoryCounter, int runningTimes) {
            this.inventoryCounter = inventoryCounter;
            this.runningTimes = runningTimes;
        }

        @Override
        public void run() {
            for (int i = 0; i < this.runningTimes; i++) {
                this.inventoryCounter.decrement();
            }
        }
    }

    static class InventoryCounter {
        private int items;

        public InventoryCounter() {
            this.items = 0;
        }

        public void increment() {
            this.items++;
        }

        public void decrement() {
            this.items--;
        }

        public int getItems() {
            return items;
        }
    }
}
