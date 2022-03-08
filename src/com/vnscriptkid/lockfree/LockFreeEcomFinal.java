package com.vnscriptkid.lockfree;

import java.util.concurrent.atomic.AtomicInteger;

public class LockFreeEcomFinal {
    public static void main(String[] args) throws InterruptedException {
        var inventoryCounter = new InventoryCounter();

        var incrementingThread = new IncrementingThread(inventoryCounter, 100000);
        var decrementingThread = new DecrementingThread(inventoryCounter, 100000);

        incrementingThread.start();

        decrementingThread.start();

        incrementingThread.join();

        decrementingThread.join();

        System.out.println("Value now: " + inventoryCounter.getItems());
    }

    static class IncrementingThread extends Thread {
        InventoryCounter counter;
        int times;

        public IncrementingThread(InventoryCounter counter, int times) {
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
        InventoryCounter counter;
        int times;

        public DecrementingThread(InventoryCounter counter, int times) {
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
        private AtomicInteger items = new AtomicInteger(0);

        public void increment() {
            this.items.incrementAndGet();
        }

        public void decrement() {
            this.items.decrementAndGet();
        }

        public int getItems() {
            return this.items.get();
        }
    }
}
