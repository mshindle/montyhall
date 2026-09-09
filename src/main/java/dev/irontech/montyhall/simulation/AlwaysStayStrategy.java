package dev.irontech.montyhall.simulation;

public class AlwaysStayStrategy implements GameStrategy {
    private static final String name = "Always Stay";

    public String displayName() {
        return name;
    }

    public boolean switchDoor() {
        return false;
    }
}
