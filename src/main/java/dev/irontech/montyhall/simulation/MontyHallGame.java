package dev.irontech.montyhall.simulation;

import java.util.concurrent.ThreadLocalRandom;

public final class MontyHallGame {

    private static final int DOOR_COUNT = 3;

    private MontyHallGame() {
    }

    /**
     * Plays a single round of the Monty Hall game using the given strategy.
     *
     * @param strategy the player's strategy for deciding whether to switch doors
     * @return {@code true} if the player ends up with the car, {@code false} otherwise
     */
    public static boolean play(GameStrategy strategy) {
        ThreadLocalRandom random = ThreadLocalRandom.current();

        int carDoor = random.nextInt(DOOR_COUNT);
        int playerDoor = strategy.chooseDoor(DOOR_COUNT);
        int hostDoor = revealHostDoor(carDoor, playerDoor, random);
        int finalDoor = applyStrategy(strategy, playerDoor, hostDoor);
        strategy.revealCarDoor(carDoor);
        return finalDoor == carDoor;
    }

    static int revealHostDoor(int carDoor, int playerDoor, ThreadLocalRandom random) {
        int candidate;
        do {
            candidate = random.nextInt(DOOR_COUNT);
        } while (candidate == playerDoor || candidate == carDoor);
        return candidate;
    }

    private static int applyStrategy(GameStrategy strategy, int playerDoor, int hostDoor) {
        if (!strategy.switchDoor(hostDoor)) {
            return playerDoor;
        }

        for (int door = 0; door < DOOR_COUNT; door++) {
            if (door != playerDoor && door != hostDoor) {
                return door;
            }
        }
        throw new IllegalStateException("Unable to determine switch door");
    }
}
