package com.soen343.SmartHomeSimulator.module.simulation.controller;

import com.soen343.SmartHomeSimulator.config.SpringContext;
import com.soen343.SmartHomeSimulator.model.repository.HomeRepository;
import com.soen343.SmartHomeSimulator.model.repository.SimulationUserRepository;
import com.soen343.SmartHomeSimulator.module.heating.model.HVAC;
import com.soen343.SmartHomeSimulator.module.simulation.model.Simulation;
import com.soen343.SmartHomeSimulator.module.simulation.repository.SimulationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * The Simuation Controller.
 */
@Slf4j
@RestController
@RequestMapping("/api")
public class SimuationController {

    /**
     * The Home repository.
     */
    public HomeRepository homeRepository;
    /**
     * The Simulation repository.
     */
    public SimulationRepository simulationRepository;
    /**
     * The Simulation user repository.
     */
    public SimulationUserRepository simulationUserRepository;

    /**
     * Instantiates a new Simuation controller.
     *
     * @param homeRepository           the home repository
     * @param simulationRepository     the simulation repository
     * @param simulationUserRepository the simulation user repository
     */
    @Autowired
    public SimuationController(HomeRepository homeRepository, SimulationRepository simulationRepository, SimulationUserRepository simulationUserRepository) {
        this.homeRepository = homeRepository;
        this.simulationRepository = simulationRepository;
        this.simulationUserRepository = simulationUserRepository;
    }

    /**
     * Update simulation response entity.
     *
     * @param simulation the simulation
     * @return the response entity
     */
//to change the simulation parameters: date, time, temp
    @PutMapping("/simulation")
    public ResponseEntity<Simulation> updateSimulation(@Valid @RequestBody Simulation simulation){
        log.info("Create this simulation {}", simulation);

        Simulation currentSimulation = simulationRepository.findById((long)1);
        currentSimulation.setDate(simulation.getDate());
        currentSimulation.setTime(simulation.getTime());
        currentSimulation.setTemperature(simulation.getTemperature());
        return ResponseEntity.ok().body(currentSimulation);
    }

    /**
     * Gets simulation.
     *
     * @return the simulation
     */
    @GetMapping("/simulation")
    public ResponseEntity<Simulation> getSimulation() {
        Simulation simulation = simulationRepository.findById((long)1);
        if (simulation == null){
            simulation = simulationRepository.findById((long)1);
        }
        return ResponseEntity.ok().body(simulation);
    }

}
