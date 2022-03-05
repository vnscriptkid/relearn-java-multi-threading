package com.vnscriptkid.interthreads;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class ProducerConsumerBasicFinal {
    public static void main(String[] args) {
        Semaphore full = new Semaphore(0);
        Semaphore empty = new Semaphore(1);
        AtomicInteger item = new AtomicInteger();

        Thread producer = new Thread(() -> {
            while (true) {
                try {
                    empty.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                item.set(new Random().nextInt(100));
                System.out.println("Producing item " + item);

                full.release();
            }
        });

        Thread consumer = new Thread(() -> {
            while (true) {
                try {
                    full.acquire();

                    System.out.println("Consuming item: " + item);

                    empty.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        producer.setDaemon(true);
        consumer.setDaemon(true);

        producer.start();
        consumer.start();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("DONE");
    }
}
