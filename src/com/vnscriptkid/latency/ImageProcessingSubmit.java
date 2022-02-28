package com.vnscriptkid.latency;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageProcessingSubmit {
    public static final String SOURCE_FILE = "./resources/many-flowers.jpg";
    public static final String DEST_FILE = "./out/many-flowers.jpg";

    public static void main(String[] args) throws IOException {
        BufferedImage originalImage = ImageIO.read(new File(SOURCE_FILE));
        BufferedImage resultImage = new BufferedImage(originalImage.getWidth(),
                originalImage.getHeight(),
                BufferedImage.TYPE_INT_RGB);

        // todo: time singleThreaded and multiThreaded (try out different numOfThreads)
        long start = System.currentTimeMillis();
//        recolorSingleThreaded(originalImage, resultImage);
        recolorMultiThreaded(originalImage, resultImage, 4);
        long end = System.currentTimeMillis();
        long duration = end - start;
        System.out.println("Duration: " + duration);

        File outputFile = new File(DEST_FILE);
        ImageIO.write(resultImage, "jpg", outputFile);
    }

    public static void recolorMultiThreaded(BufferedImage originalImage, BufferedImage resultImage, int numOfThreads) {
        // todo: takes in numberOfThreads as arg
        // todo: init a list of threads
        List<Thread> threads = new ArrayList<>();
        // todo: divide whole image into parts horizontally (by height)
        // todo: each part is processed by one thread, need to calculate yStart of each portion
        int dividedHeight = originalImage.getHeight() / numOfThreads;

        for (int i = 0; i < numOfThreads; i++) {
            int threadMultiplier = i;
            threads.add(new Thread(() -> {
                int xStart = 0;
                int yStart = threadMultiplier * dividedHeight;
                recolorImage(originalImage,
                        resultImage,
                        xStart,
                        yStart,
                        originalImage.getWidth(),
                        dividedHeight);
            }));
        }
        // todo: start each thread
        for(Thread thread: threads) {
            thread.start();
        }
        // todo: hold on the main thread until all threads are done.
        for(Thread thread: threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void recolorSingleThreaded(BufferedImage originalImage, BufferedImage resultImage) {
        // todo: recolor whole image in one thread
        recolorImage(originalImage, resultImage, 0, 0, originalImage.getWidth(), originalImage.getHeight());
    }

    public static void recolorImage(BufferedImage originalImage, BufferedImage resultImage, int xStart, int yStart,
                                    int width, int height) {
        // todo: process every pixel from top to bottom, left to right, recolor (x,y)
        for (int x = xStart; x < xStart + width && x < originalImage.getWidth(); x++) {
            for (int y = yStart; y < yStart + height && y < originalImage.getHeight(); y++) {
                recolorPixel(originalImage, resultImage, x, y);
            }
        }
    }

    public static void recolorPixel(BufferedImage originalImage, BufferedImage resultImage, int x, int y) {
        int rgb = originalImage.getRGB(x, y);

        int red = getRed(rgb);
        int green = getGreen(rgb);
        int blue = getBlue(rgb);

        int newRed;
        int newGreen;
        int newBlue;

        if (isShadeOfGray(red, green, blue)) {
            newRed = Math.min(255, red + 10);
            newGreen = Math.max(0, green - 80);
            newBlue = Math.max(0, blue - 20);
        } else {
            newRed = red;
            newGreen = green;
            newBlue = blue;
        }
        int newRGB = createRGBFromColors(newRed, newGreen, newBlue);
        setRGB(resultImage, x, y, newRGB);
    }

    public static void setRGB(BufferedImage image, int x, int y, int rgb) {
        image.getRaster().setDataElements(x, y, image.getColorModel().getDataElements(rgb, null));
    }

    public static boolean isShadeOfGray(int red, int green, int blue) {
        return Math.abs(red - green) < 30
                && Math.abs(red - blue) < 30
                && Math.abs(green - blue) < 30;
    }

    public static int createRGBFromColors(int red, int green, int blue) {
        int rgb = 0;

        rgb |= blue;
        rgb |= green << 8;
        rgb |= red << 16;

        rgb |= 0xFF000000;

        return rgb;
    }

    public static int getRed(int rgb) {
        return (rgb & 0x00FF0000) >> 16;
    }

    public static int getGreen(int rgb) {
        return (rgb & 0x0000FF00) >> 8;
    }

    public static int getBlue(int rgb) {
        return rgb & 0x000000FF;
    }
}

