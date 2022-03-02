package com.vnscriptkid.concurrencychallenges;

import java.util.Random;

public class MetricsEx {
    public static void main(String[] args) {
        // todo: what is the shared resource here
        Metrics metrics = new Metrics();
        BusinessLogic businessLogic1 = new BusinessLogic(metrics);
        BusinessLogic businessLogic2 = new BusinessLogic(metrics);
        MetricsPrinter metricsPrinter = new MetricsPrinter(metrics);

        businessLogic1.start();
        businessLogic2.start();
        metricsPrinter.start();
    }

    public static class MetricsPrinter extends Thread {
        Metrics metrics;

        public MetricsPrinter(Metrics metrics) {
            this.metrics = metrics;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Average now: " + metrics.getAverage());
            }
        }
    }

    public static class BusinessLogic extends Thread {
        Metrics metrics;

        public BusinessLogic(Metrics metrics) {
            this.metrics = metrics;
        }

        @Override
        public void run() {
            while (true) {
                long start = System.currentTimeMillis();

                try {
                    Thread.sleep(new Random().nextInt(10));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                long end = System.currentTimeMillis();

                this.metrics.addSample(end - start);
            }
        }
    }

    public static class Metrics {
        long count = 0;
        double average = 0.0;

        // todo: what is wrong with this, how it could become wrong
        public void addSample(long sample) {
            double currentSum = count * average;

            count++;

            average = (currentSum + sample) / count;
        }

        // todo: what is wrong with this, what case it belongs to, what are the fixes, pros and cons
        public double getAverage() {
            return average;
        }
    }
}
