package dev.irontech.montyhall.simulation.strategies;

import java.util.concurrent.ThreadLocalRandom;

import dev.irontech.montyhall.simulation.GameStrategy;

public class CoinFlipStrategy implements GameStrategy {
    private static final String name = "Coin Flip";

    public String displayName() {
        return name;
    }

    @Override
    public int chooseDoor(int doorCount) {
        return ThreadLocalRandom.current().nextInt(doorCount);
    }

    public boolean switchDoor(int hostDoor) {
        return ThreadLocalRandom.current().nextBoolean();
    }

    @Override
    public void revealCarDoor(int carDoor) {
        return;
    }
}
