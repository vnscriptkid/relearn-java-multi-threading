package com.vnscriptkid.thread.creation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CrackPasswordEx {
    public static final int MAX_PASSWORD = 9999;

    public static void main(String[] args) {
        Random random = new Random();

        // todo: construct vault with a random int password from 0 to 9999
        Vault vault = new Vault();

        List<Thread> threads = new ArrayList<>();

        // todo: add 2 hackers and 1 police to threads list (polymorphism)

        // todo: start all 3 threads
    }

    private static class Vault {
        // todo: construct vault that has a password

        public boolean isCorrectPassword(int guess) {
            // todo: check against guess, simulate checking time of 5 millis
            return false;
        }
    }

    // todo: make this base class and should be constructed directly
    // todo: make it run on a thread
    // todo: it keeps a ref to vault that it wants to crack
    // todo: set name of thread to name of class from which object is constructed
    // todo: set max priority for this thread
    private static class HackerThread extends Thread {
        protected Vault vault;

        public HackerThread() {

        }

        @Override
        public void start() {
            super.start();
        }
        // todo: inject log `Starting thread: thread-name` before thread is started (override start() method of Thread class)
    }

    // todo: make this a hacker thread
    private static class AscendingHackerThread {

        public AscendingHackerThread() {

        }

        // todo: when running, it starts counting from 0 to MAX_PASSWORD, and check if it's correct vault password
        // todo: if true, print current hacker name and his guess, then exit
    }

    // todo: make this a hacker thread
    private static class DescendingHackerThread {

        public DescendingHackerThread() {

        }

        // todo: when running, it starts counting from MAX_PASSWORD to 0, and check if it's correct vault password
        // todo: if true, print current hacker name and his guess, then exit
    }

    // todo: make this run on one thread
    private static class PoliceThread {
        // todo: when running, it counts down 10 secs, when time over then print `game over for hackers`, exit
    }
}

