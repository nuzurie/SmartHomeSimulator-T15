package com.soen343.SmartHomeSimulator.module.simulation.controller;

import com.soen343.SmartHomeSimulator.config.SpringContext;
import com.soen343.SmartHomeSimulator.model.Home;
import com.soen343.SmartHomeSimulator.model.HouseLayoutFormatException;
import com.soen343.SmartHomeSimulator.model.repository.HomeRepository;
import com.soen343.SmartHomeSimulator.model.repository.SimulationUserRepository;
import com.soen343.SmartHomeSimulator.module.heating.model.HVAC;
import com.soen343.SmartHomeSimulator.module.simulation.model.Simulation;
import com.soen343.SmartHomeSimulator.module.simulation.repository.SimulationRepository;
import com.soen343.SmartHomeSimulator.service.SimulationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.net.jsse.JSSEImplementation;
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
     * The Simulation service.
     */
    private SimulationService simulationService;

    /**
     * Instantiates a new Simuation controller.
     *
     * @param simulationService           The Simulation service.
     */
    @Autowired
    public SimuationController(SimulationService simulationService) {
        this.simulationService = simulationService;
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
        Simulation currentSimulation = simulationService.updateSimulation(simulation);
        return ResponseEntity.ok().body(currentSimulation);
    }

    /**
     * Gets simulation.
     *
     * @return the simulation
     */
    @GetMapping("/simulation")
    public ResponseEntity<Simulation> getSimulation() {
        Simulation simulation = simulationService.getSimulation();
        return ResponseEntity.ok().body(simulation);
    }


    @PostMapping
    public ResponseEntity<Simulation> createHouse(@RequestBody Object object) {
        Simulation simulation = simulationRepository.findById((long) 1);
        Home home;
        try{
            home = (Home) object;
        }
        catch (HouseLayoutFormatException e){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
        simulation.setHome(home);
        return ResponseEntity.ok().body(simulation);
    }

}
