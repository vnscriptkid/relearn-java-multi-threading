package com.vnscriptkid.lockfree;

public class LockFreeEcomEx {
    public static void main(String[] args) throws InterruptedException {
        var inventoryCounter = new LockFreeEcomFinal.InventoryCounter();

        var incrementingThread = new LockFreeEcomFinal.IncrementingThread(inventoryCounter, 100000);
        var decrementingThread = new LockFreeEcomFinal.DecrementingThread(inventoryCounter, 100000);

        incrementingThread.start();

        decrementingThread.start();

        incrementingThread.join();

        decrementingThread.join();

        System.out.println("Value now: " + inventoryCounter.getItems());
    }

    static class IncrementingThread extends Thread {
        LockFreeEcomFinal.InventoryCounter counter;
        int times;

        public IncrementingThread(LockFreeEcomFinal.InventoryCounter counter, int times) {
            this.counter = counter;
            this.times = times;
        }

        @Override
        public void run() {
            for (var i = 0; i < this.times; i++) {
                this.counter.increment();
            }
        }
    }

    static class DecrementingThread extends Thread {
        LockFreeEcomFinal.InventoryCounter counter;
        int times;

        public DecrementingThread(LockFreeEcomFinal.InventoryCounter counter, int times) {
            this.counter = counter;
            this.times = times;
        }

        @Override
        public void run() {
            for (var i = 0; i < this.times; i++) {
                this.counter.decrement();
            }
        }
    }

    static class InventoryCounter {
        private int items = 0;

        public void increment() {
            items++;
        }

        public void decrement() {
            items--;
        }

        public int getItems() {
            return items;
        }
    }
}
