package dev.irontech.montyhall.web;

import dev.irontech.montyhall.simulation.SimulationResult;
import dev.irontech.montyhall.simulation.SimulationService;
import dev.irontech.montyhall.simulation.Strategy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SimulationController.class)
class SimulationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SimulationService simulationService;

    @Test
    void runSimulationsWithValidRunsReturnsOkWithResults() throws Exception {
        List<SimulationResult> results = List.of(
                new SimulationResult(Strategy.ALWAYS_SWITCH, 667, 333, 1000),
                new SimulationResult(Strategy.ALWAYS_STAY, 333, 667, 1000),
                new SimulationResult(Strategy.COIN_FLIP, 500, 500, 1000)
        );
        when(simulationService.runSimulations(anyInt())).thenReturn(results);

        mockMvc.perform(post("/api/simulations")
                        .contentType("application/json")
                        .content("{\"runs\": 1000}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].strategy").value("ALWAYS_SWITCH"));
    }

    @Test
    void runSimulationsWithNonPositiveRunsReturnsBadRequest() throws Exception {
        mockMvc.perform(post("/api/simulations")
                        .contentType("application/json")
                        .content("{\"runs\": 0}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void runSimulationsWithMissingBodyReturnsBadRequest() throws Exception {
        mockMvc.perform(post("/api/simulations")
                        .contentType("application/json")
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }
}
