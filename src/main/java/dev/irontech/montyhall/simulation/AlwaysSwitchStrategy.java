package dev.irontech.montyhall.simulation;

public class AlwaysSwitchStrategy implements GameStrategy {
    private static final String name = "Always Switch";

    public String displayName() {
        return name;
    }

    public boolean switchDoor() {
        return true;
    }
}
