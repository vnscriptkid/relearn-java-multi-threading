package com.vnscriptkid.interthreads.matrixmultiply;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.StringJoiner;

public class MatrixGeneratorSubmit {
    private static final String OUTPUT_FILE = "./out/matrices";
    private static final int N = 10;
    private static final int NUMBER_OF_MATRIX_PAIRS = 100000;

    public static void main(String[] args) throws IOException {
        // todo: create file object represents the file at `OUTPUT_FILE`
        File file = new File(OUTPUT_FILE);
        // todo: create fileWriter from file object
        FileWriter fileWriter = new FileWriter(file);
        // todo: create matrices and write to file
        createMatrices(fileWriter);
        // todo: flush and close fileWriter
        fileWriter.flush();
        fileWriter.close();
    }

    private static float[] createRow(Random random) {
        float[] row = new float[N];
        // todo: loop n times, assign random value to row[i]
        for (int i = 0; i < N; i++) {
            row[i] = random.nextFloat() * random.nextInt(100);
        }

        return row;
    }

    private static float[][] createMatrix(Random random) {
        float matrix[][] = new float[N][N];
        // todo: loop n times, each iteration create one row
        for (int i = 0; i < N; i++) {
            matrix[i] = createRow(random);
        }
        return matrix;
    }

    private static void saveMatrixToFile(FileWriter fileWriter, float[][] matrix) throws IOException {
        for (int r = 0; r < N; r++) {
            // todo: use stringJoiner to join one row
            StringJoiner stringJoiner = new StringJoiner(",");
            for (int c = 0; c < N; c++) {
                // todo: format number into string in form: #.##
                stringJoiner.add(String.format("%.2f", matrix[r][c]));
            }
            // todo: write one line into file
            fileWriter.write(stringJoiner.toString());
            // todo: separate with next line by \n
            fileWriter.write("\n");
        }
        // todo: separate next matrix by \n
        fileWriter.write("\n");
    }

    private static void createMatrices(FileWriter fileWriter) throws IOException {
        // todo: create 2*p matrices, save each to file
        Random random = new Random();
        for (int i = 0; i < NUMBER_OF_MATRIX_PAIRS * 2; i++) {
            float[][] matrix = createMatrix(random);
            saveMatrixToFile(fileWriter, matrix);
        }
    }
}
