package com.vnscriptkid.interthreads.matrixmultiply;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.StringJoiner;

public class MainApplicationSubmit {
    private static final String INPUT_FILE = "./out/matrices";
    private static final String OUTPUT_FILE = "./out/matrices_results.txt";
    private static final int N = 10;

    public static void main(String[] args) throws IOException {
        ThreadSafeQueue threadSafeQueue = new ThreadSafeQueue();
        File inputFile = new File(INPUT_FILE);
        File outputFile = new File(OUTPUT_FILE);

        MatricesReaderProducer matricesReader = new MatricesReaderProducer(new FileReader(inputFile), threadSafeQueue);
        MatricesMultiplierConsumer matricesConsumer = new MatricesMultiplierConsumer(new FileWriter(outputFile), threadSafeQueue);

        matricesConsumer.start();
        matricesReader.start();
    }

    private static class MatricesMultiplierConsumer extends Thread {
        private ThreadSafeQueue queue;
        private FileWriter fileWriter;

        public MatricesMultiplierConsumer(FileWriter fileWriter, ThreadSafeQueue queue) {
            this.fileWriter = fileWriter;
            this.queue = queue;
        }

        private static void saveMatrixToFile(FileWriter fileWriter, float[][] matrix) throws IOException {
            for (int r = 0; r < N; r++) {
                StringJoiner stringJoiner = new StringJoiner(", ");
                for (int c = 0; c < N; c++) {
                    stringJoiner.add(String.format("%.2f", matrix[r][c]));
                }
                fileWriter.write(stringJoiner.toString());
                fileWriter.write('\n');
            }
            fileWriter.write('\n');
        }

        @Override
        public void run() {
            while (true) {
                MatricesPair matricesPair = queue.remove(); // potentially, being blocked here

                if (matricesPair == null) {
                    System.out.println("No more matrices to read from the queue, consumer is terminating");
                    break;
                }

                float[][] result = multiplyMatrices(matricesPair.matrix1, matricesPair.matrix2);

                try {
                    saveMatrixToFile(fileWriter, result);
                } catch (IOException e) {
                }
            }

            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private float[][] multiplyMatrices(float[][] m1, float[][] m2) {
            float[][] result = new float[N][N];

            // todo: calculate using 3 nested loops
            for (int r = 0; r < N; ++r) {
                for (int c = 0; c < N; ++c) {
                    for (int k = 0; k < N; k++) {
                        result[r][c] += m1[r][k] * m2[k][c];
                    }
                }
            }

            return result;
        }
    }

    private static class MatricesReaderProducer extends Thread {
        private Scanner scanner;
        private ThreadSafeQueue queue;

        public MatricesReaderProducer(FileReader reader, ThreadSafeQueue queue) {
            this.scanner = new Scanner(reader);
            this.queue = queue;
        }

        @Override
        public void run() {
            while (true) {
                float[][] matrix1 = readMatrix();
                float[][] matrix2 = readMatrix();
                // todo: when either matrix is null, we're done, update queue state, stop current thread
                if (matrix1 == null || matrix2 == null) {
                    queue.terminate();
                    return;
                }

                MatricesPair matricesPair = new MatricesPair();
                matricesPair.matrix1 = matrix1;
                matricesPair.matrix2 = matrix2;

                queue.add(matricesPair);
            }
        }

        private float[][] readMatrix() {
            // todo: init matrix
            float[][] matrix = new float[N][N];
            // todo: loop n rows, each row, if not hasNext then can't build complete matrix, return null
            for (int r = 0; r < N; r++) {
                if (!scanner.hasNext()) return null;
                // todo: read line using nextLine, split by `, `
                var line = scanner.nextLine().split(",");

                if (line.length != N) return null;

                // todo: convert str into float
                for (int c = 0; c < N; c++) {
                    matrix[r][c] = Float.valueOf(line[c]);
                }
            }
            // todo: read the empty line before the next matrix
            scanner.nextLine();

            return matrix;
        }
    }

    private static class ThreadSafeQueue {
        private Queue<MatricesPair> queue = new LinkedList<>();
        private boolean isEmpty = true;
        private boolean isTerminate = false; // done yet?
        private static final int CAPACITY = 5;

        // todo: producer reads one matricesPair from file then add it to queue
        // todo: fix issue that queue size is unbounded, producer keeps reading and adding to the queue
        // hint: must be a way to tell producer to wait for a bit when queue size reaches CAPACITY
        public synchronized void add(MatricesPair matricesPair) {
            // todo: add to queue, update state, notify consumers
            while (queue.size() == CAPACITY) {
                try {
                    wait();
                } catch (InterruptedException e) {
                }
            }
            queue.add(matricesPair);
            System.out.println("queue size: " + queue.size());
            isEmpty = false;
            notify();
        }

        public synchronized MatricesPair remove() {
            // todo: take out the next matricesPair out of matrix and return
            // todo: CASE 1: nothing in queue and not done yet => nothing to consume now, wait for a bit
            // todo: expect that when producer adding new stuff to queue, he'll wake me up
            // todo: CASE 2: 1 item in queue, after consuming that only item, size is 0 => update state in advance
            // todo: CASE 3: if nothing in queue and done, then return null
            MatricesPair pair = null;

            while (isEmpty && !isTerminate) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (isEmpty && isTerminate) return null;

            if (queue.size() == 1) isEmpty = true;

            System.out.println("queue size " + queue.size());

            // todo: take one out from queue
            pair = queue.poll();

            if (queue.size() == CAPACITY - 1) {
                notify();
            }
            // todo: when queue size is one less than CAPACITY, notify producer to continue his work
            return pair;
        }

        public synchronized void terminate() {
            isTerminate = true;
            notifyAll();
        }
    }

    private static class MatricesPair {
        public float[][] matrix1;
        public float[][] matrix2;
    }
}
