package dev.irontech.montyhall.simulation.strategies;

import dev.irontech.montyhall.simulation.GameStrategy;

import java.util.concurrent.ThreadLocalRandom;

public class AlwaysStayStrategy implements GameStrategy {
    private static final String name = "Always Stay";

    public String displayName() {
        return name;
    }

    @Override
    public int chooseDoor(int doorCount) {
        return ThreadLocalRandom.current().nextInt(doorCount);
    }

    public boolean switchDoor(int hostDoor) {
        return false;
    }

    @Override
    public void revealCarDoor(int carDoor) {
        return;
    }
}
