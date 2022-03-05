package com.vnscriptkid.advancedlocking;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteDatabaseFinal {
    public static final int HIGHEST_PRICE = 1000;

    public static void main(String[] args) {
        InventoryDatabase inventoryDatabase = new InventoryDatabase();

        // seed database
        Random random = new Random();
        for (int i = 0; i < 100000; i++) {
            inventoryDatabase.addItem(random.nextInt(HIGHEST_PRICE));
        }

        // start a writing thread as daemon, write intermittently every 10 millis sec
        Thread writeThread = new Thread(() -> {
            while (true) {
                inventoryDatabase.addItem(random.nextInt(HIGHEST_PRICE));
                inventoryDatabase.removeItem(random.nextInt(HIGHEST_PRICE));

                // wait a bit
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        writeThread.setDaemon(true);
        writeThread.start();

        // start multiple reading thread
        int numOfReaders = 7;
        long startAt = System.currentTimeMillis();
        List<Thread> readingThreads = new ArrayList();

        for (int i = 0; i < numOfReaders; i++) {
            readingThreads.add(new Thread(() -> {
                for (int j = 0; j < 100000; j++) {
                    int upperBound = random.nextInt(HIGHEST_PRICE);
                    int lowerBound = upperBound > 0 ? random.nextInt(upperBound) : 0;

                    inventoryDatabase.getNumberOfItemsInPriceRange(lowerBound, upperBound);
                }
            }));
        }

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
        private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        private Lock readLock = lock.readLock();
        private Lock writeLock = lock.writeLock();

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
