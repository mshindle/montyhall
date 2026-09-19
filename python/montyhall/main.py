import argparse
from .simulation import run_simulation
from .strategies import AlwaysSwitch, AlwaysStay, CoinFlip

def parse_cmdline() -> argparse.Namespace:
    parser = argparse.ArgumentParser(description="Run Monty Hall strategy simulations.")
    parser.add_argument(
        "-r", "--runs",
        type=int,
        default=1_000_000,
        help="number of simulation runs per strategy (default: %(default)s)",
    )
    return parser.parse_args()

def main():
    args = parse_cmdline()
    total_runs = args.runs
    strategies = [AlwaysStay(), AlwaysSwitch(), CoinFlip()]

    print(f"Running {total_runs:,} simulations per strategy...")
    results = run_simulation(strategies, total_runs)
    print("\n--- Results ---")
    for res in results:
        print(f"🎯 {res.strategy}: {res.win_percent:.2f}% win rate ({res.car_count:,} cars, {res.goat_count:,} goats)")

# Press the green button in the gutter to run the script.
if __name__ == '__main__':
    main()

