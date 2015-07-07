package com.soat;

/**
 * a simple benchmark attempt of logarithm funciton
 *
 */
public class MyBenchmark {

    public static void main(String[] args) {
        // start stopwatch
        long startTime = System.nanoTime();
        // Here is the code to measure
        double log42 = Math.log(42);
        // stop stopwatch
        long endTime = System.nanoTime();

        System.out.println("log(42) is computed in : " + (endTime - startTime) + " ns");

    }

}
