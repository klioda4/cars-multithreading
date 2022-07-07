package ru.clevertec.kli.carsmultithreading;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class RacingCar {

    /**
     * Speed in meters per second
     */
    private final String name;
    private int speed;
    private int coordinate;
}
