package com.vnscriptkid.advancedlocking;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteDatabaseSubmit {
    public static final int HIGHEST_PRICE = 1000;

    public static void main(String[] args) {
        InventoryDatabase inventoryDatabase = new InventoryDatabase();

        // todo: seed database, add 100000 random item to inventory
        Random random = new Random();
        for (int i = 0; i < 100000; i++) {
            inventoryDatabase.addItem(random.nextInt(HIGHEST_PRICE));
        }

        // todo: start one writing, write intermittently every 10 millis sec, runs as long as main thread still alive
        // todo: while writeThread running, add and remove item with random prices
        Thread writeThread = new Thread(() -> {
            inventoryDatabase.addItem(random.nextInt(HIGHEST_PRICE));
            inventoryDatabase.removeItem(random.nextInt(HIGHEST_PRICE));
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        writeThread.setDaemon(true);
        writeThread.start();

        // start multiple reading thread
        int numOfReaders = 7;
        long startAt = System.currentTimeMillis();
        List<Thread> readingThreads = new ArrayList();

        // todo: init list of 7 readingThreads
        // todo: while readingThreads running, random lowerBound and upperBound, call getNumberOfItemsInPriceRange
        for (int i = 0; i < numOfReaders; i++) {
            readingThreads.add(new Thread(() -> {
                for (int j = 0; j < 100000; j++) {
                    int upperBound = random.nextInt(HIGHEST_PRICE);
                    int lowerBound = upperBound > 0 ? random.nextInt(upperBound) : 0;

                    inventoryDatabase.getNumberOfItemsInPriceRange(lowerBound, upperBound);
                }
            }));
        }
        // todo: start every thread, tell main thread to wait for all readingThreads
        // todo: measure duration from start to end
        for (Thread thread: readingThreads) {
            thread.start();
        }

        for (Thread thread: readingThreads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long endAt = System.currentTimeMillis();
        long duration = endAt - startAt;
        System.out.println("Duration: " + duration);

    }

    static class InventoryDatabase {
        private TreeMap<Integer, Integer> priceToCountMap = new TreeMap<>();
        // todo: switch to use readWriteLock, compare the performance
        // todo: explain why it's better, what cases should we use it
        // 1) we have dominant read operations
        // 2) using readWriteLock, allows concurrent reads against shared resource
        private ReentrantLock lock = new ReentrantLock();
        private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        private Lock readLock = readWriteLock.readLock();
        private Lock writeLock = readWriteLock.writeLock();

        // READ operation
        public int getNumberOfItemsInPriceRange(int lowerBound, int upperBound) {
            readLock.lock();
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
                readLock.unlock();
            }
        }

        // WRITE operation
        public void addItem(int price) {
            writeLock.lock();
            try {
                if (!priceToCountMap.containsKey(price)) priceToCountMap.put(price, 1);
                else priceToCountMap.put(price, priceToCountMap.get(price) + 1);
            } finally {
                writeLock.unlock();
            }
        }

        // READ operation
        public void removeItem(int price) {
            writeLock.lock();
            try {
                if (!priceToCountMap.containsKey(price)) return;

                int currentCount = priceToCountMap.get(price);

                if (currentCount == 1) priceToCountMap.remove(price);
                else priceToCountMap.put(price, currentCount - 1);
            } finally {
                writeLock.unlock();
            }
        }
    }
}
