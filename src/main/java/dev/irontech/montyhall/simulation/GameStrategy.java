package dev.irontech.montyhall.simulation;

public interface GameStrategy {
    String displayName();
    int chooseDoor(int doorCount);
    boolean switchDoor(int hostDoor);
    void revealCarDoor(int carDoor);
}
