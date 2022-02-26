package com.vnscriptkid.thread.creation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CrackPasswordSubmit1 {
    public static final int MAX_PASSWORD = 999;

    public static void main(String[] args) {
        Random random = new Random();

        // todo: construct vault with a random int password from 0 to 9999
        Vault vault = new Vault(random.nextInt(MAX_PASSWORD));

        List<Thread> threads = new ArrayList<>();

        // todo: add 2 hackers and 1 police to threads list (polymorphism)
        threads.add(new AscendingHackerThread(vault));
        threads.add(new DescendingHackerThread(vault));
        threads.add(new PoliceThread());

        // todo: start all 3 threads
        for (Thread thread: threads) {
            thread.start();
        }
    }

    private static class Vault {
        // todo: construct vault that has a password
        private int password;

        public Vault(int password) {
            this.password = password;
        }

        public boolean isCorrectPassword(int guess) {
            // todo: check against guess, simulate checking time of 5 millis
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return false;
            }

            return this.password == guess;
        }
    }

    // todo: make this base class and should be constructed directly
    // todo: make it run on a thread
    // todo: it keeps a ref to vault that it wants to crack
    // todo: set name of thread to name of class from which object is constructed
    // todo: set max priority for this thread
    private static abstract class HackerThread extends Thread {
        protected Vault vault;

        public HackerThread(Vault vault) {
            this.vault = vault;
            this.setName(this.getClass().getSimpleName());
            this.setPriority(Thread.MAX_PRIORITY);
        }

        @Override
        public void start() {
            System.out.println("Starting thread " + this.getName());
            super.start();
        }
        // todo: inject log `Starting thread: thread-name` before thread is started (override start() method of Thread class)
    }

    // todo: make this a hacker thread
    private static class AscendingHackerThread extends HackerThread {

        public AscendingHackerThread(Vault vault) {
            super(vault);
        }

        // todo: when running, it starts counting from 0 to MAX_PASSWORD, and check if it's correct vault password
        // todo: if true, print current hacker name and his guess, then exit


        @Override
        public void run() {
            for (int i = 0; i <= MAX_PASSWORD; i++) {
                if (this.vault.isCorrectPassword((i))) {
                    System.out.println(this.getName() + " guessed " + i);
                    System.exit(0);
                }
            }
        }
    }

    // todo: make this a hacker thread
    private static class DescendingHackerThread extends HackerThread {

        public DescendingHackerThread(Vault vault) {
            super(vault);
        }

        // todo: when running, it starts counting from MAX_PASSWORD to 0, and check if it's correct vault password
        // todo: if true, print current hacker name and his guess, then exit


        @Override
        public void run() {
            for (int i = MAX_PASSWORD; i >= 0; i--) {
                if (this.vault.isCorrectPassword(i)) {
                    System.out.println(this.getName() + " guessed " + i);
                    System.exit(0);
                }
            }
        }
    }

    // todo: make this run on one thread
    private static class PoliceThread extends Thread {
        // todo: when running, it counts down 10 secs, when time over then print `game over for hackers`, exit

        @Override
        public void run() {
            for (int i = 10; i > 0; i--) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Police is coming. " + i + " secs left.");
            }
            System.out.println("Game over for hackers");
            System.exit(0);
        }
    }
}

