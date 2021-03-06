package com.vnscriptkid.datasharing;

public class InventoryCounterFinal {
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
