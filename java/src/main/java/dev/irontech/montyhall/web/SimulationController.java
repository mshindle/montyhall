package dev.irontech.montyhall.web;

import dev.irontech.montyhall.simulation.SimulationResult;
import dev.irontech.montyhall.simulation.SimulationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SimulationController {

    private final SimulationService simulationService;

    public SimulationController(SimulationService simulationService) {
        this.simulationService = simulationService;
    }

    @PostMapping("/api/simulations")
    public ResponseEntity<List<SimulationResult>> runSimulations(@RequestBody(required = false) SimulationRequest request) {
        if (request == null || request.runs() <= 0) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(simulationService.runSimulations(request.runs()));
    }
}
