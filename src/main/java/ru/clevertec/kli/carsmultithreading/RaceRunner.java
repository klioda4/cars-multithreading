package ru.clevertec.kli.carsmultithreading;

import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RaceRunner {

    private static final boolean USE_DEFAULT_VALUES = false;

    public static void main(String[] args) {
        System.out.print("Cars number: ");
        int carsNumber = USE_DEFAULT_VALUES ? 5 : consoleScanPositiveValue();
        System.out.print("Track length in meters: ");
        int trackLength = USE_DEFAULT_VALUES ? 500 : consoleScanPositiveValue();

        ExecutorService threadPool = Executors.newFixedThreadPool(carsNumber);
        CountDownLatch countDownLatch = new CountDownLatch(carsNumber);
        for (int i = 0; i < carsNumber; i++) {
            threadPool.submit(new CarMover(
                new RacingCar("Racer" + i),
                trackLength,
                countDownLatch));
        }
        threadPool.shutdown();
    }

    private static int consoleScanPositiveValue() {
        Scanner scanner = new Scanner(System.in);
        int result = -1;
        try {
            result = Integer.parseInt(scanner.next());
            if (result <= 0) {
                throw new IllegalArgumentException("Must be greater than zero");
            }
        } catch (Exception e) {
            System.out.println("Incorrect value");
            System.exit(1);
        }
        return result;
    }
}
