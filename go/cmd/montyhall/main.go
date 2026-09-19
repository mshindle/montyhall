package main

import (
	"context"
	"flag"
	"fmt"
	"runtime"

	"github.com/mshindle/montyhall/internal/game"
	"github.com/mshindle/montyhall/internal/simulation"
	"github.com/mshindle/montyhall/internal/strategy"
)

const defaultTotalRuns = 1_000_000

func main() {
	var totalRuns int
	flag.IntVar(&totalRuns, "runs", defaultTotalRuns, "number of simulation runs")
	flag.Parse()

	ctx := context.Background()

	// create our strategies to apply
	strategies := []game.Strategy{
		&strategy.AlwaysSwitch{},
		&strategy.AlwaysStay{},
		&strategy.CoinFlip{},
	}

	// num workers based on CPU
	numWorkers := max(runtime.GOMAXPROCS(0)-1, 1)

	// execute it
	s := simulation.New(strategies, numWorkers)
	results, err := s.RunSimulation(ctx, totalRuns)
	if err != nil {
		fmt.Printf("error running simulation: %v\n", err)
		return
	}

	for _, result := range results {
		fmt.Printf("🎯 %15s: %5.2f win rate (%d cars, %d goats)\n",
			result.Strategy, result.WinPercentage(), result.CarCount, result.GoatCount)
	}
}
