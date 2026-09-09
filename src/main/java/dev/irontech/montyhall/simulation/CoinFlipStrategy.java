package dev.irontech.montyhall.simulation;

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
