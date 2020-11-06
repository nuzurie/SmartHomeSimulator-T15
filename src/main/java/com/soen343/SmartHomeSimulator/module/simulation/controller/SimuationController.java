package com.soen343.SmartHomeSimulator.module.simulation.controller;

import com.soen343.SmartHomeSimulator.model.Home;
import com.soen343.SmartHomeSimulator.model.SimulationUser;
import com.soen343.SmartHomeSimulator.model.repository.HomeRepository;
import com.soen343.SmartHomeSimulator.model.repository.SimulationUserRepository;
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
    public SimulationUserRepository simulationUserRepository;

    @Autowired
    public SimuationController(HomeRepository homeRepository, SimulationRepository simulationRepository, SimulationUserRepository simulationUserRepository) {
        this.homeRepository = homeRepository;
        this.simulationRepository = simulationRepository;
        this.simulationUserRepository = simulationUserRepository;
    }

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

    @PutMapping("/simulation/user-rooms")
    public ResponseEntity<Simulation> updateUserRooms(@Valid @RequestBody Simulation simulation){
        //We aren't changing the rooms and home in respective repositories yet.
        //It is not yet required, but if required later, this change MUST be made.
        Simulation currentSimulation = simulationRepository.findById((long)1);
        log.info("Before saving the simulation is {}", simulationRepository.findAll());
        currentSimulation.setHome(simulation.getHome());
        currentSimulation.setSimulationUsers(simulation.getSimulationUsers());

        System.out.println("Received sim " + simulation);
        log.info("The received simulation has rooms:", simulation.getHome().getRooms() );

        log.info("After saving the simulation is {}", simulationRepository.findAll());
        return ResponseEntity.ok().body(currentSimulation);
    }

    @GetMapping("/simulation")
    public ResponseEntity<Simulation> getSimulation() {
        Simulation simulation = simulationRepository.findById((long)1);
        if (simulation == null){
            simulation = simulationRepository.findById((long)1);
        }
        log.info("After getting the simulation is {}", simulationRepository.findAll());
        System.out.println(simulation);
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
    public ResponseEntity deleteSimulation(Simulation simulation) {
        simulationRepository.deleteById(simulation.getId());
        System.out.println(simulationRepository.findAll());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("simulation/loginUser/{id}")
    public ResponseEntity<SimulationUser> updateLoginUser(@PathVariable long id){
        SimulationUser newLoginUser = simulationUserRepository.findById(id);
        Simulation simulation = simulationRepository.findById((long)1);
        simulation.setLoggedInUser(newLoginUser);

        return ResponseEntity.ok().body(newLoginUser);
    }

}
