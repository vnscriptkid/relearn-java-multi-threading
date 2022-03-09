package com.vnscriptkid.lockfree;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;

public class ThreadSafeStackEx {
    public static void main(String[] args) throws InterruptedException {
        ThreadSafeStackFinal.LockFreeStack stack = new ThreadSafeStackFinal.LockFreeStack();
        Random random = new Random();

        // feed stack
        for (int i = 0; i < 100000; i++) {
            stack.push(random.nextInt());
        }

        List<Thread> threads = new ArrayList<>();

        int numOfPushThreads = 3;
        int numOfPopThreads = 3;

        for (int i = 0; i < numOfPushThreads; i++) {
            Thread thread = new Thread(() -> {
                while (true) {
                    stack.push(random.nextInt());
                }
            });
            thread.setDaemon(true);
            threads.add(thread);
        }

        for (int i = 0; i < numOfPopThreads; i++) {
            Thread thread = new Thread(() -> {
                while (true) {
                    stack.pop();
                }
            });
            thread.setDaemon(true);
            threads.add(thread);
        }

        // start all
        for (Thread thread: threads) {
            thread.start();
        }

        Thread.sleep(5000);

        System.out.println("Number of operations " + stack.getOperationsCount());
    }

    static class LockFreeStack<T> {
        AtomicReference<ThreadSafeStackFinal.StackNode<T>> head = new AtomicReference<>(null);
        AtomicInteger operationsCount = new AtomicInteger(0);

        public void push(T value) {
            // todo: create new node
            // todo: (1) read from head (2) write to head
            // todo: make sure head is not changed from (1) -> (2), hint: infinite loop + compareAndSet
            // todo: increment ops count
        }

        public T pop() {
            // todo
            return null;
        }

        public int getOperationsCount() {
            return operationsCount.get();
        }
    }

    static class StandardStack<T> {
        private ThreadSafeStackFinal.StackNode<T> head = null;
        private int operationsCount = 0;

        public synchronized void push(T value) {
            ThreadSafeStackFinal.StackNode<T> newNode = new ThreadSafeStackFinal.StackNode<>(value);

            newNode.next = head;
            head = newNode;

            operationsCount++;
        }

        public synchronized T pop() {
            if (head == null) {
                operationsCount++;
                return null;
            }

            T headValue = head.value;

            head = head.next;
            operationsCount++;

            return headValue;
        }

        public int getOperationsCount() {
            return operationsCount;
        }
    }

    static class StackNode<T> {
        public T value;
        public ThreadSafeStackFinal.StackNode<T> next;

        public StackNode(T value) {
            this.value = value;
            this.next = null;
        }
    }
}
