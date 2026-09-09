package dev.irontech.montyhall.simulation.strategies;

import dev.irontech.montyhall.simulation.GameStrategy;

public class AlwaysSwitchStrategy implements GameStrategy {
    private static final String name = "Always Switch";

    public String displayName() {
        return name;
    }

    public boolean switchDoor() {
        return true;
    }
}
