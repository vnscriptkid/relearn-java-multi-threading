package com.vnscriptkid.interthreads.matrixmultiply;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class MatricesGeneratorEx {
    private static final String OUTPUT_FILE = "./out/matrices";
    private static final int N = 10;
    private static final int NUMBER_OF_MATRIX_PAIRS = 100000;

    public static void main(String[] args) throws IOException {
        // todo: create file object represents the file at `OUTPUT_FILE`
        // todo: create fileWriter from file object
        // todo: create matrices and write to file
        // todo: flush and close fileWriter
    }

    private static float[] createRow(Random random) {
        // todo: loop n times, assign random value to row[i]
        return new float[N];
    }

    private static float[][] createMatrix(Random random) {
        float matrix[][] = new float[N][N];
        // todo: loop n times, each iteration create one row
        return matrix;
    }

    private static void saveMatrixToFile(FileWriter fileWriter, float[][] matrix) throws IOException {
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                // todo: use stringJoiner to join one row
                // todo: format number into string in form: #.##
            }
            // todo: write one line into file
            // todo: separate with next line by \n
        }
        // todo: separate next matrix by \n
    }

    private static void createMatrices(FileWriter fileWriter) throws IOException {
        // todo: create 2*p matrices, save each to file
    }
}
