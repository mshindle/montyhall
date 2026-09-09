package dev.irontech.montyhall.simulation.strategies;

import dev.irontech.montyhall.simulation.GameStrategy;

import java.util.concurrent.ThreadLocalRandom;

public class CoinFlipStrategy implements GameStrategy {
    private static final String name = "Coin Flip";

    public String displayName() {
        return name;
    }

    public boolean switchDoor() {
        return ThreadLocalRandom.current().nextBoolean();
    }
}
