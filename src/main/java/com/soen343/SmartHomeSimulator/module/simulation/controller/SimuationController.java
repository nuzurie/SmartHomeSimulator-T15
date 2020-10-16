package com.soen343.SmartHomeSimulator.module.simulation.controller;

import com.soen343.SmartHomeSimulator.model.Home;
import com.soen343.SmartHomeSimulator.model.repository.HomeRepository;
import com.soen343.SmartHomeSimulator.module.simulation.model.Simulation;
import com.soen343.SmartHomeSimulator.module.simulation.repository.SimulationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api")
public class SimuationController {

    public HomeRepository homeRepository;
    public SimulationRepository simulationRepository;

    public SimuationController(HomeRepository homeRepository, SimulationRepository simulationRepository) {
        this.homeRepository = homeRepository;
        this.simulationRepository = simulationRepository;
    }

    @PutMapping("/simulation")
    public ResponseEntity<Simulation> updateSimulation(@Valid @RequestBody Simulation simulation){
        log.info("Create this simulation {}", simulation);
        Simulation currentSimulation = simulationRepository.findById("1").get();
        simulation.setName("1");
        simulation.setHome(currentSimulation.getHome());
        simulation.setSimulationUser(currentSimulation.getSimulationUser());
        Simulation createdSimulation = simulationRepository.save(simulation);

        System.out.println(createdSimulation);
        return ResponseEntity.ok().body(createdSimulation);
    }
}
