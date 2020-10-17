package com.soen343.SmartHomeSimulator.module.simulation.controller;

import com.soen343.SmartHomeSimulator.model.Home;
import com.soen343.SmartHomeSimulator.model.repository.HomeRepository;
import com.soen343.SmartHomeSimulator.module.simulation.model.Simulation;
import com.soen343.SmartHomeSimulator.module.simulation.repository.SimulationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        simulation.setSimulationUsers(currentSimulation.getSimulationUsers());
        Simulation createdSimulation = simulationRepository.save(simulation);

        System.out.println(createdSimulation);
        return ResponseEntity.ok().body(createdSimulation);
    }

    @PutMapping("/simulation/user-rooms")
    public ResponseEntity<Simulation> updateUserRooms(@Valid @RequestBody Simulation simulation){

        Simulation currentSimulation = simulationRepository.findById("1").get();
        log.info("Before saving the simulation is {}", simulationRepository.findAll());
        currentSimulation.setHome(simulation.getHome());
        currentSimulation.setSimulationUsers(simulation.getSimulationUsers());

        System.out.println("Received sim " + simulation);
        log.info("The received simulation has rooms:", simulation.getHome().getRooms() );
        Simulation createdSimulation = simulationRepository.save(simulation);

        log.info("The saving simulation is {}", createdSimulation);
        log.info("After saving the simulation is {}", simulationRepository.findAll());
        return ResponseEntity.ok().body(createdSimulation);
    }

    @GetMapping("/simulation")
    public ResponseEntity<Simulation> getSimulation() {
        Simulation simulation = simulationRepository.findById("1").get();
        if (simulation == null){
            simulation = simulationRepository.findById("2").get();
        }
        log.info("After getting the simulation is {}", simulationRepository.findAll());
        return ResponseEntity.ok().body(simulation);
    }

    @PostMapping("/simulation/user-rooms")
    public ResponseEntity<Simulation> postUserRooms(@Valid @RequestBody Simulation simulation){

        log.info("Before saving the simulation is {}", simulationRepository.findAll());
        System.out.println("Received sim " + simulation);
        Simulation createdSimulation = simulationRepository.save(simulation);
        System.out.println("ThSaving sin " + createdSimulation);

        log.info("After saving the simulation is {}", simulationRepository.findAll());
        return ResponseEntity.ok().body(createdSimulation);
    }

    @DeleteMapping("/simulation")
    public ResponseEntity deleteSimulation() {
        simulationRepository.deleteAll();
        System.out.println(simulationRepository.findAll());
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
