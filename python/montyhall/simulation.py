import os
import itertools
import montyhall.game
from concurrent.futures import ProcessPoolExecutor
from functools import reduce
from .strategies import GameStrategy
from dataclasses import dataclass, replace

@dataclass
class SimulationResult:
    strategy: str
    car_count: int
    goat_count: int
    total_runs: int

    @property
    def win_percent(self) -> float:
        if self.total_runs == 0:
            return 0.0
        return 100.0 * self.car_count / self.total_runs

    def combine(self, other: SimulationResult) -> SimulationResult:
        """Combines this result with another result and returns a new SimulationResult."""
        if not isinstance(other, SimulationResult):
            raise TypeError("Can only combine with another SimulationResult instance.")
        if other.strategy != self.strategy:
            raise ValueError("Can only combine results from the same strategy.")

        # Calculate the new combined values
        new_car = self.car_count + other.car_count
        new_goat = self.goat_count + other.goat_count
        new_runs = self.total_runs + other.total_runs

        # Create and return a brand-new instance safely
        return replace(self, car_count=new_car, goat_count=new_goat, total_runs=new_runs)

    def __add__(self, other: SimulationResult) -> SimulationResult:
        """Combines this result with another result and returns a new SimulationResult."""
        return self.combine(other)


def run_simulation(strategies: list[GameStrategy], runs: int) -> list[SimulationResult]:
    num_workers = max(1, os.cpu_count() - 1)
    base_chunk = max(1, runs // num_workers)
    remainder = runs % num_workers
    results = []

    with ProcessPoolExecutor(max_workers=num_workers) as executor:
        for strategy in strategies:
            strat_arg = itertools.repeat(strategy, num_workers)
            chunk_arg = [base_chunk+remainder] + [base_chunk]*(num_workers-1)

            results_iter = executor.map(batch_simulate, strat_arg, chunk_arg)
            agg_result = reduce(lambda a, b: a + b, results_iter)
            results.append(agg_result)
    return results

def batch_simulate(strategy: GameStrategy, runs: int) -> SimulationResult:
    car_count = 0
    for i in range(runs):
        if montyhall.game.play(strategy):
            car_count += 1
    goat_count = runs - car_count
    return SimulationResult(strategy.display_name(), car_count, goat_count, runs)
