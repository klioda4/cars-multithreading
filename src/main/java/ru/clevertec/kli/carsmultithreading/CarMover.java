package ru.clevertec.kli.carsmultithreading;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CarMover implements Runnable {

    private static final int MIN_SPEED = 1;
    private static final int MAX_SPEED = 20;
    private static final int SPEED_DIFFERENCE = MAX_SPEED - MIN_SPEED + 1;

    private final RacingCar car;
    private final int trackLength;
    private final CountDownLatch countDownLatch;

    @Override
    public void run() {
        countDownLatch.countDown();
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            System.out.println(car.getName() + "disqualified");
            return;
        }

        while (!isCarReachedFinish()) {
            car.setSpeed(calculateSpeed());
            if (sleepSecondOrCatchInterrupt()) {
                System.out.println(car.getName() + "disqualified");
                return;
            }
            updateCoordinate();
            printDistanceToFinish();
        }
    }

    private int calculateSpeed() {
        return new Random().nextInt(SPEED_DIFFERENCE) + MIN_SPEED;
    }

    private void updateCoordinate() {
        int newCoordinate = car.getCoordinate() + car.getSpeed();
        car.setCoordinate(newCoordinate);
    }

    private boolean isCarReachedFinish() {
        return car.getCoordinate() >= trackLength;
    }

    /**
     * @return true if InterruptedException have caught
     */
    private boolean sleepSecondOrCatchInterrupt() {
        try {
            Thread.sleep(1000);
            return false;
        } catch (InterruptedException e) {
            return true;
        }
    }

    private void printDistanceToFinish() {
        if (isCarReachedFinish()) {
            System.out.println(car.getName() + " has finished");
        } else {
            System.out.printf("For %s %d meters left\n",
                car.getName(),
                trackLength - car.getCoordinate());
        }
    }
}
