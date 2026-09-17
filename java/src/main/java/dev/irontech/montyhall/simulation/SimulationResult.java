package dev.irontech.montyhall.simulation;

public record SimulationResult(String strategy, int carCount, int goatCount, int totalRuns) {

    public double winPercentage() {
        return totalRuns == 0 ? 0.0 : 100.0 * carCount / totalRuns;
    }
}
