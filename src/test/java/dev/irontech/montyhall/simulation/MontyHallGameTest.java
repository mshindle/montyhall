package dev.irontech.montyhall.simulation;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MontyHallGameTest {

    private static final int SAMPLE_SIZE = 100_000;
    private static final double TOLERANCE = 0.02;

    @RepeatedTest(50)
    void hostNeverRevealsPlayerDoorOrCarDoor() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int carDoor = random.nextInt(3);
        int playerDoor = random.nextInt(3);

        int hostDoor = MontyHallGame.revealHostDoor(carDoor, playerDoor, random);

        assertNotEquals(playerDoor, hostDoor, "Host must not reveal the player's chosen door");
        assertNotEquals(carDoor, hostDoor, "Host must not reveal the car door");
    }

    @Test
    void alwaysSwitchWinsAboutTwoThirdsOfTheTime() {
        GameStrategy gs = new AlwaysSwitchStrategy();
        double winRate = winRateOver(gs);
        assertTrue(Math.abs(winRate - (2.0 / 3.0)) < TOLERANCE,
                "Expected ~2/3 win rate for ALWAYS_SWITCH, got " + winRate);
    }

    @Test
    void alwaysStayWinsAboutOneThirdOfTheTime() {
        GameStrategy gs = new AlwaysStayStrategy();
        double winRate = winRateOver(gs);
        assertTrue(Math.abs(winRate - (1.0 / 3.0)) < TOLERANCE,
                "Expected ~1/3 win rate for ALWAYS_STAY, got " + winRate);
    }

    @Test
    void coinFlipWinsAboutHalfTheTime() {
        GameStrategy gs = new CoinFlipStrategy();
        double winRate = winRateOver(gs);
        assertTrue(Math.abs(winRate - 0.5) < TOLERANCE,
                "Expected ~1/2 win rate for COIN_FLIP, got " + winRate);
    }

    private double winRateOver(GameStrategy strategy) {
        int wins = 0;
        for (int i = 0; i < SAMPLE_SIZE; i++) {
            if (MontyHallGame.play(strategy)) {
                wins++;
            }
        }
        return (double) wins / SAMPLE_SIZE;
    }
}
