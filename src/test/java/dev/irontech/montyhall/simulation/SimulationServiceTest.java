package dev.irontech.montyhall.simulation;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SimulationServiceTest {

    private static final List<GameStrategy> strategies = List.of(
            new AlwaysSwitchStrategy(),
            new AlwaysStayStrategy(),
            new CoinFlipStrategy()
    );

    @Test
    void runSimulationsReturnsResultForEachStrategy() {
        SimulationService service = new SimulationService(strategies);
        int runs = 1_000;

        List<SimulationResult> results = service.runSimulations(runs);

        assertEquals(strategies.size(), results.size());
        for (SimulationResult result : results) {
            assertEquals(runs, result.totalRuns());
            assertEquals(runs, result.carCount() + result.goatCount());
        }

        service.shutdown();
    }

    @Test
    void runSimulationsHandlesSingleRun() {
        SimulationService service = new SimulationService(strategies);

        List<SimulationResult> results = service.runSimulations(1);

        assertEquals(strategies.size(), results.size());
        for (SimulationResult result : results) {
            assertEquals(1, result.totalRuns());
            assertEquals(1, result.carCount() + result.goatCount());
        }

        service.shutdown();
    }
}
