package com.soen343.SmartHomeSimulator.module.security.controller;

import com.soen343.SmartHomeSimulator.module.simulation.model.Simulation;
import com.soen343.SmartHomeSimulator.module.simulation.repository.SimulationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;

@Slf4j
@RestController
@RequestMapping("/api")
public class SecurityController {

    SimulationRepository simulationRepository;
    Security security;

    @Autowired
    public SecurityController(SimulationRepository simulationRepository, Security security) {
        this.simulationRepository = simulationRepository;
        this.security = security;
    }

    @PutMapping("/security/awayMode")
    public ResponseEntity<?> toggleAwayMode(){
        log.info("in toggleAwayMode");
        Simulation currentSimulation = simulationRepository.findById((long)1);
        int success = currentSimulation.toggleAwayMode();
        log.info("Success" , success);
        ArrayList<Long> messages;
        if (success==-1)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("House isn't empty.");
        else{
            System.out.println("got 2");
            messages = security.awayModeProtocol(currentSimulation.getHome());
        }
        System.out.println("got 1");
        return ResponseEntity.ok().body(messages);

    }


}
