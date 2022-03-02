package com.vnscriptkid.datasharing;

public class InventoryCounterEx {
    public static void main(String[] args) throws InterruptedException {
        var inventoryCounter = new InventoryCounter();

        // todo: create 2 threads

        // todo: make them run sequentially, see result

        // todo: then make them run concurrently

        // todo: what is the cause of issue?

        // todo: make sure this line is executed when both threads are done
        System.out.println("Value now: " + inventoryCounter.getItems());
    }

    // todo: make this runs on one thread, works on one inventoryCounter object
    static class IncrementingThread {
        // todo: increment inventoryCounter times times while running
    }

    // todo: make DecrementingThread similar to IncrementingThread

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
