package com.soen343.SmartHomeSimulator.module.core.controller;

import com.soen343.SmartHomeSimulator.model.SimulationUser;
import com.soen343.SmartHomeSimulator.model.repository.SimulationUserRepository;
import com.soen343.SmartHomeSimulator.module.simulation.model.Simulation;
import com.soen343.SmartHomeSimulator.module.simulation.repository.SimulationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api")
public class CoreController {

    private SimulationRepository simulationRepository;
    private SimulationUserRepository simulationUserRepository;

    @Autowired
    public CoreController(SimulationRepository simulationRepository, SimulationUserRepository simulationUserRepository) {
        this.simulationRepository = simulationRepository;
        this.simulationUserRepository = simulationUserRepository;
    }

    @PutMapping("/simulation/update")
    public ResponseEntity<?> updateUserRooms(@Valid @RequestBody Simulation simulation) {
        //We aren't changing the rooms and home in respective repositories yet.
        //It is not yet required, but if required later, this change MUST be made.
        Simulation currentSimulation = simulationRepository.findById((long) 1);

        currentSimulation.setHome(simulation.getHome());
        currentSimulation.setSimulationUsers(simulation.getSimulationUsers());
        currentSimulation.setAutoMode();

        if (currentSimulation.notifyObserver() == -1) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Someone has intruded");
        }

        return ResponseEntity.ok().body(currentSimulation);
    }

    @PutMapping("simulation/loginUser/{id}")
    public ResponseEntity<SimulationUser> updateLoginUser(@PathVariable long id) {
        SimulationUser newLoginUser = simulationUserRepository.findById(id);
        Simulation simulation = simulationRepository.findById((long) 1);
        simulation.setLoggedInUser(newLoginUser);

        return ResponseEntity.ok().body(newLoginUser);
    }

    @PutMapping("simulation/autoMode")
    public ResponseEntity<?> toggleAutoMode() {

        Simulation simulation = simulationRepository.findById((long) 1);
        simulation.setLightsAutoMode(!simulation.isLightsAutoMode());
        simulation.setAutoMode();
        log.info("Sim", simulation);
        return ResponseEntity.ok().build();

    }

    @PutMapping("simulation/time-multiplier/{multiplier_s}")
    public ResponseEntity setTimeMultiplier(@PathVariable String multiplier_s) {
        double multiplier = Double.valueOf(multiplier_s);
        log.info("The multiplier is: ", Double.valueOf(multiplier_s));
        Simulation simulation = simulationRepository.findById((long) 1);
        simulation.setTimeMultiplier(multiplier);

        return ResponseEntity.ok().build();
    }
}
