package com.soen343.SmartHomeSimulator.module.security.controller;

import com.soen343.SmartHomeSimulator.module.simulation.model.Simulation;
import com.soen343.SmartHomeSimulator.module.simulation.repository.SimulationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;

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

    @PutMapping("simulation/call-timer/{timer}")
    public ResponseEntity callAuthoritiesTimer(@PathVariable String timer) {
        System.out.println(timer);
        double callTimer = Double.valueOf(timer);
        Simulation simulation = simulationRepository.findById((long) 1);
        simulation.setCallAuthoritiesTimer(callTimer);

        return ResponseEntity.ok().build();
    }

    @GetMapping("simulation/callAuthorities")
    public DeferredResult<ResponseEntity<?>> callAuthorities() {
        final DeferredResult <ResponseEntity < ? >> out = new DeferredResult <>((long)100000);

        ForkJoinPool.commonPool().submit(() -> {
            Simulation simulation = simulationRepository.findById((long) 1);
            double timer = simulation.getCallAuthoritiesTimer();
            double multiplier = simulation.getTimeMultiplier();
            timer = timer*60*1000/multiplier;
            try {
                Thread.sleep(Math.round(timer));
            } catch (InterruptedException e) {
            }
            out.setResult(ResponseEntity.ok().build());
        });

        return out;
    }

}
