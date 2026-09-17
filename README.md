# Monty Hall Problem Simulator

A multi-language repository containing simulations of the classic [Monty Hall problem](https://en.wikipedia.org/wiki/Monty_Hall_problem), implemented in **Java** (Spring Boot Web application with interactive UI and REST API) and **Python** (high-performance CLI tool using multiprocessing).

---

## What is the Monty Hall Problem?

The Monty Hall problem is a famous probability puzzle based on the American television game show *Let's Make a Deal*:

1. **The Setup**: You are presented with three closed doors. Behind one door is a prize (**Car**), and behind the other two doors are booby prizes (**Goats**).
2. **Initial Choice**: You pick one door (e.g., Door 1).
3. **The Reveal**: The host (Monty Hall), who knows what is behind every door, opens one of the other two doors (e.g., Door 3) to reveal a goat.
4. **The Dilemma**: Monty gives you a choice: stick with your initial pick (Door 1) or switch to the remaining unopened door (Door 2).

### The Math
While intuition suggests a 50/50 chance, switching doubles your chances of winning:
- **Always Stay**: Wins **1/3 (~33.33%)** of the time (the initial probability of picking the car).
- **Always Switch**: Wins **2/3 (~66.67%)** of the time (wins whenever the initial pick was a goat).
- **Coin Flip (Random 50/50)**: Wins **1/2 (~50.00%)** of the time.

This repository simulates hundreds of thousands or millions of rounds across strategies to demonstrate the theoretical probabilities converging empirically.

---

## Repository Structure

```
montyhall/
├── java/                        # Java / Spring Boot application
│   ├── src/main/java/           # Simulation engine, strategies, REST controller
│   ├── src/main/resources/      # Static web UI (HTML/CSS/JS) & configuration
│   ├── src/test/java/           # Unit & integration test suite
│   ├── build.gradle             # Gradle build configuration
│   └── README.md                # Java-specific documentation
│
├── python/                      # Python package & CLI application
│   ├── montyhall/               # Simulation logic, game engine, strategies, CLI entrypoint
│   ├── pyproject.toml           # Project metadata & dependencies (Hatchling)
│   └── uv.lock                  # Lockfile
│
├── HELP.md                      # Reference links
├── LICENSE                      # Project license
└── README.md                    # Root project documentation (this file)
```

---

## Implementations

### 1. Java (Spring Boot Web App)

Located in `java/`. Provides an interactive web UI and a REST API to run simulations across configured strategies in parallel using Java virtual/platform threads.

- **Prerequisites**: Java 21+
- **Run the Web Application**:
  ```bash
  cd java
  ./gradlew bootRun
  ```
  Open **http://localhost:8080** in your browser to use the graphical simulator.
- **Run Tests**:
  ```bash
  cd java
  ./gradlew test
  ```
- **REST API Endpoint**:
  ```bash
  curl -s -X POST http://localhost:8080/api/simulations \
       -H "Content-Type: application/json" \
       -d '{"runs": 100000}'
  ```

For more details on extending strategies or configuration, see [`java/README.md`](java/README.md).

---

### 2. Python (CLI & Multiprocessing Engine)

Located in `python/`. Provides a CLI simulation engine that leverages Python's `concurrent.futures.ProcessPoolExecutor` to distribute batches of simulation rounds across CPU cores.

- **Prerequisites**: Python 3.14+ (or Python 3.10+ compatible runtime) / [`uv`](https://github.com/astral-sh/uv)
- **Run the CLI Simulator**:
  ```bash
  cd python
  python3 -m montyhall.main
  ```
  Or using `uv`:
  ```bash
  cd python
  uv run montyhall
  ```

---

## JetBrains IDE Configuration

To work on this repository inside IntelliJ IDEA / PyCharm:
1. **Java Module**: Link `java/build.gradle` as a Gradle project via the **Gradle** tool window.
2. **Python Module**: Add `python/` as a Python module in **Project Structure** (`File` -> `Project Structure` -> `Modules`) and attach a Python interpreter / virtualenv.
