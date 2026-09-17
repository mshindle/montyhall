# Monty Hall Problem Simulator

A Spring Boot web application that simulates the classic [Monty Hall problem](https://en.wikipedia.org/wiki/Monty_Hall_problem) at scale, comparing different door-switching strategies side-by-side.

## What is the Monty Hall Problem?

You are on a game show. There are three doors: behind one is a car, behind the other two are goats. You pick a door. The host — who knows what's behind each door — opens one of the remaining doors to reveal a goat. Now you face a choice: stick with your original door, or switch to the other unopened door.

Intuitively, it feels like a 50/50 decision. Mathematically it is not: **switching wins the car roughly 2/3 of the time**, while staying wins only 1/3 of the time.

This simulator runs thousands (or millions) of rounds per strategy so you can see the probabilities converge in real time.

---

## Prerequisites

- **Java 21** or later (the Gradle wrapper handles everything else)

---

## Building

```bash
# Compile and package
./gradlew build

# Run the test suite only
./gradlew test
```

---

## Running

```bash
./gradlew bootRun
```

Then open your browser at **http://localhost:8080**.

---

## Using the App

1. Enter the **number of simulation runs per strategy** in the form (default: 100 000).
2. Click **Run Simulation**.
3. The results table shows each strategy's **car count**, **goat count**, and **win percentage**.

---

## REST API

### `POST /api/simulations`

Run the simulation programmatically.

**Request**

```json
{ "runs": 100000 }
```

| Field | Type | Description |
|-------|------|-------------|
| `runs` | integer | Number of rounds to play per strategy (must be > 0) |

**Example**

```bash
curl -s -X POST http://localhost:8080/api/simulations \
     -H "Content-Type: application/json" \
     -d '{"runs": 100000}' | jq .
```

**Response** — JSON array, one object per strategy:

```json
[
  {
    "strategy":      "Always Switch",
    "carCount":      66712,
    "goatCount":     33288,
    "totalRuns":     100000,
    "winPercentage": 66.71
  },
  {
    "strategy":      "Always Stay",
    "carCount":      33301,
    "goatCount":     66699,
    "totalRuns":     100000,
    "winPercentage": 33.30
  },
  {
    "strategy":      "Coin Flip",
    "carCount":      49987,
    "goatCount":     50013,
    "totalRuns":     100000,
    "winPercentage": 49.99
  }
]
```

---

## Project Structure

```
src/main/
├── java/dev/irontech/montyhall/
│   ├── MontyHallApplication.java      # @SpringBootApplication; registers strategy beans
│   ├── simulation/
│   │   ├── GameStrategy.java          # Interface all strategies must implement
│   │   ├── MontyHallGame.java         # Plays a single round given a strategy
│   │   ├── SimulationResult.java      # Result record (counts + win %)
│   │   ├── SimulationService.java     # Runs all strategies in parallel
│   │   └── strategies/
│   │       ├── AlwaysSwitchStrategy.java  # Built-in: always switch doors
│   │       ├── AlwaysStayStrategy.java    # Built-in: never switch doors
│   │       └── CoinFlipStrategy.java      # Built-in: switch randomly 50 / 50
│   └── web/
│       ├── SimulationController.java  # POST /api/simulations endpoint
│       └── SimulationRequest.java     # Request body DTO
└── resources/
    └── static/                        # Single-page UI (HTML + CSS + JS)
```

---

## Adding Your Own Strategy

Any class that implements the `GameStrategy` interface is automatically picked up by the simulator.

### The interface

```java
public interface GameStrategy {
    /** Name shown in the UI and API results. */
    String displayName();

    /** Return true to switch doors, false to stay. */
    boolean switchDoor();
}
```

### Step 1 — Implement the interface

Create a new file alongside the other strategies, e.g. `src/main/java/dev/irontech/montyhall/simulation/strategies/MyCustomStrategy.java`:

```java
package dev.irontech.montyhall.simulation.strategies;

public class MyCustomStrategy implements GameStrategy {

    @Override
    public String displayName() {
        return "My Custom Strategy";
    }

    @Override
    public boolean switchDoor() {
        // Replace with your own logic.
        // Return true to switch, false to stay.
        return Math.random() < 0.75; // switches 75 % of the time
    }
}
```

### Step 2 — Register the strategy

Open `MontyHallApplication.java` and add your class to the `gameStrategies()` bean:

```java
@Bean
public List<GameStrategy> gameStrategies() {
    return List.of(
        new AlwaysSwitchStrategy(),
        new AlwaysStayStrategy(),
        new CoinFlipStrategy(),
        new MyCustomStrategy()   // <-- add your strategy here
    );
}
```

### Step 3 — Rebuild and run

```bash
./gradlew bootRun
```

Your strategy will appear automatically in the results table and in the REST API response.
