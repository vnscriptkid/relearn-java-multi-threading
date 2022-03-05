package com.vnscriptkid.interthreads;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class ProducerConsumerBasicEx {
    public static void main(String[] args) {
        Semaphore full = new Semaphore(0);
        Semaphore empty = new Semaphore(1);
        AtomicInteger item = new AtomicInteger();

        Thread producer = new Thread(() -> {
            while (true) {
                // todo: sync 2 threads
            }
        });

        Thread consumer = new Thread(() -> {
            while (true) {
                // todo: sync 2 threads
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
