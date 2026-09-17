package dev.irontech.montyhall;

import dev.irontech.montyhall.simulation.strategies.AlwaysStayStrategy;
import dev.irontech.montyhall.simulation.strategies.AlwaysSwitchStrategy;
import dev.irontech.montyhall.simulation.strategies.CoinFlipStrategy;
import dev.irontech.montyhall.simulation.GameStrategy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class MontyHallApplication {

    public static void main(String[] args) {
        SpringApplication.run(MontyHallApplication.class, args);
    }

    @Bean
    public List<GameStrategy> gameStrategies() {
        return List.of(new AlwaysSwitchStrategy(), new AlwaysStayStrategy(), new CoinFlipStrategy());
    }
}
