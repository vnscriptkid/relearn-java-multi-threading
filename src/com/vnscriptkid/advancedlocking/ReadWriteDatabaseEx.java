package com.vnscriptkid.advancedlocking;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public class ReadWriteDatabaseEx {
    public static final int HIGHEST_PRICE = 1000;

    public static void main(String[] args) {
        ReadWriteDatabaseFinal.InventoryDatabase inventoryDatabase = new ReadWriteDatabaseFinal.InventoryDatabase();

        // todo: seed database, add 100000 random item to inventory

        // todo: start one writing, write intermittently every 10 millis sec, runs as long as main thread still alive
        // todo: while writeThread running, add and remove item with random prices

        // start multiple reading thread
        int numOfReaders = 7;
        long startAt = System.currentTimeMillis();
        List<Thread> readingThreads = new ArrayList();

        // todo: init list of 7 readingThreads
        // todo: while readingThreads running, random lowerBound and upperBound, call getNumberOfItemsInPriceRange
        // todo: start every thread, tell main thread to wait for all readingThreads
        // todo: measure duration from start to end
    }

    static class InventoryDatabase {
        private TreeMap<Integer, Integer> priceToCountMap = new TreeMap<>();
        // todo: switch to use readWriteLock, compare the performance
        // todo: explain why it's better, what cases should we use it
        private ReentrantLock lock = new ReentrantLock();

        // READ operation
        public int getNumberOfItemsInPriceRange(int lowerBound, int upperBound) {
            lock.lock();
            try {
                int fromKey = priceToCountMap.ceilingKey(lowerBound);

                int toKey = priceToCountMap.floorKey(upperBound);

                NavigableMap<Integer, Integer> rangeOfPrices =
                        priceToCountMap.subMap(fromKey, true, toKey, true);

                int sum = 0;

                for (int numOfItemsForPrice : rangeOfPrices.values()) {
                    sum += numOfItemsForPrice;
                }

                return sum;
            } finally {
                lock.unlock();
            }
        }

        // WRITE operation
        public void addItem(int price) {
            lock.lock();
            try {
                if (!priceToCountMap.containsKey(price)) priceToCountMap.put(price, 1);
                else priceToCountMap.put(price, priceToCountMap.get(price) + 1);
            } finally {
                lock.unlock();
            }
        }

        // READ operation
        public void removeItem(int price) {
            lock.lock();
            try {
                if (!priceToCountMap.containsKey(price)) return;

                int currentCount = priceToCountMap.get(price);

                if (currentCount == 1) priceToCountMap.remove(price);
                else priceToCountMap.put(price, currentCount - 1);
            } finally {
                lock.unlock();
            }
        }
    }
}
