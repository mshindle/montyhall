from .simulation import run_simulation
from .strategies import AlwaysSwitch, AlwaysStay, CoinFlip



def main():
    strategies = [AlwaysStay(), AlwaysSwitch(), CoinFlip()]
    total_runs = 1_000_000

    print(f"Running {total_runs:,} simulations per strategy...")
    results = run_simulation(strategies, total_runs)
    print("\n--- Results ---")
    for res in results:
        print(f"🎯 {res.strategy}: {res.win_percent:.2f}% win rate ({res.car_count:,} cars, {res.goat_count:,} goats)")

# Press the green button in the gutter to run the script.
if __name__ == '__main__':
    main()

