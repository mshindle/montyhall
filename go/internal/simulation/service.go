package simulation

import (
	"context"

	"github.com/mshindle/montyhall/internal/game"
	"golang.org/x/sync/errgroup"
)

type Result struct {
	Strategy  string
	CarCount  int
	GoatCount int
	TotalRuns int
}

func (r Result) WinPercentage() float64 {
	if r.TotalRuns == 0 {
		return 0.0
	}
	return float64(r.CarCount) / float64(r.TotalRuns) * 100.0
}

type Service struct {
	numWorkers int
	strategies []game.Strategy
}

func New(strategies []game.Strategy, numWorkers int) *Service {
	return &Service{
		numWorkers: numWorkers,
		strategies: strategies,
	}
}

func (s *Service) RunSimulation(ctx context.Context, runs int) ([]Result, error) {
	args := s.makeRunArgs(runs)
	results := make([]Result, len(s.strategies)*len(args))
	g, ctx := errgroup.WithContext(ctx)
	g.SetLimit(s.numWorkers)

	for n, strat := range s.strategies {
		for i, chunk := range args {
			if ctx.Err() != nil {
				break
			}
			g.Go(func() error {
				r, err := s.simulate(ctx, strat, chunk)
				if err != nil {
					return err
				}
				idx := n*len(args) + i
				results[idx] = r
				return nil
			})
		}
	}
	if err := g.Wait(); err != nil {
		return nil, err
	}

	// reduce results
	bin := make(map[string]Result)
	for _, result := range results {
		val, ok := bin[result.Strategy]
		if !ok {
			val = Result{}
			val.Strategy = result.Strategy
		}
		val.CarCount += result.CarCount
		val.GoatCount += result.GoatCount
		val.TotalRuns += result.TotalRuns
		bin[result.Strategy] = val
	}

	// ensure output is in the same order
	out := make([]Result, 0, len(s.strategies))
	for _, strat := range s.strategies {
		out = append(out, bin[strat.DisplayName()])
	}
	return out, nil
}

func (s *Service) simulate(ctx context.Context, strategy game.Strategy, chunk int) (Result, error) {
	const ctxCheckMask = 1<<16 - 1
	carCount := 0
	for i := range chunk {
		if i&ctxCheckMask == 0 && ctx.Err() != nil {
			return Result{}, ctx.Err()
		}
		if game.Play(strategy) {
			carCount++
		}
	}
	return Result{
		Strategy:  strategy.DisplayName(),
		CarCount:  carCount,
		GoatCount: chunk - carCount,
		TotalRuns: chunk,
	}, nil
}

func (s *Service) makeRunArgs(runs int) []int {
	baseChunk := runs / s.numWorkers
	if baseChunk == 0 {
		baseChunk = 1
	}
	remainder := runs % s.numWorkers

	args := make([]int, s.numWorkers)
	for i := range args {
		args[i] = baseChunk
		if i == 0 {
			args[i] += remainder
		}
	}
	return args
}
