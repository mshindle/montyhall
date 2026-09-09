package dev.irontech.montyhall.simulation;

import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@Service
public class SimulationService {

    private final ExecutorService executorService;
    private final List<GameStrategy> strategies;

    public SimulationService(List<GameStrategy> strategies) {
        this.strategies = strategies;

        int availableCores = Runtime.getRuntime().availableProcessors();
        int workerThreads = Math.max(1, availableCores - 1);
        this.executorService = Executors.newFixedThreadPool(workerThreads);
    }

    public List<SimulationResult> runSimulations(int runsPerStrategy) {
        List<Callable<SimulationResult>> tasks = new ArrayList<>();
        for (GameStrategy strategy : strategies) {
            tasks.add(() -> simulateStrategy(strategy, runsPerStrategy));
        }

        try {
            List<Future<SimulationResult>> futures = executorService.invokeAll(tasks);
            List<SimulationResult> results = new ArrayList<>();
            for (Future<SimulationResult> future : futures) {
                results.add(future.get());
            }
            return results;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Simulation was interrupted", e);
        } catch (ExecutionException e) {
            throw new IllegalStateException("Simulation task failed", e.getCause());
        }
    }

    private SimulationResult simulateStrategy(GameStrategy strategy, int runs) {
        int carCount = 0;
        for (int i = 0; i < runs; i++) {
            if (MontyHallGame.play(strategy)) {
                carCount++;
            }
        }
        int goatCount = runs - carCount;
        return new SimulationResult(strategy.displayName(), carCount, goatCount, runs);
    }

    @PreDestroy
    public void shutdown() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            executorService.shutdownNow();
        }
    }
}
