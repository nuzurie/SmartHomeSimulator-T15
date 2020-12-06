package com.soen343.SmartHomeSimulator.module.security.controller;

import com.soen343.SmartHomeSimulator.model.Light;
import com.soen343.SmartHomeSimulator.module.security.model.AwayModeLights;
import com.soen343.SmartHomeSimulator.module.security.model.AwayModeTime;
import com.soen343.SmartHomeSimulator.module.simulation.model.Simulation;
import com.soen343.SmartHomeSimulator.module.simulation.repository.SimulationRepository;
import com.soen343.SmartHomeSimulator.service.SecurityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

/**
 * The Security controller.
 */
@Slf4j
@RestController
@RequestMapping("/api")
public class SecurityController {

    /**
     * The Simulation repository.
     */
    SimulationRepository simulationRepository;
    /**
     * The Security.
     */
    Security security;

    /**
     * The Security service.
     */
     SecurityService securityService;

    /**
     * Instantiates a new Security controller.
     *
     * @param simulationRepository the simulation repository
     * @param security             the security
     */
    @Autowired
    public SecurityController(SimulationRepository simulationRepository, Security security, SecurityService securityService) {
        this.simulationRepository = simulationRepository;
        this.security = security;
        this.securityService = securityService;
    }

    /**
     * Toggle away mode response entity.
     *
     * @return the response entity
     */
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
            messages = security.awayModeProtocol(currentSimulation.getHome());
        }
        return ResponseEntity.ok().body(messages);
    }

    /**
     * Call authorities timer response entity.
     *
     * @param timer the timer
     * @return the response entity
     */
    @PutMapping("simulation/call-timer/{timer}")
    public ResponseEntity callAuthoritiesTimer(@PathVariable String timer) {
        securityService.callAuthoritiesTimer(timer);
        return ResponseEntity.ok().build();
    }

    /**
     * Call authorities deferred result.
     *
     * @return the deferred result
     */
    @GetMapping("simulation/callAuthorities")
    public DeferredResult<ResponseEntity<?>> callAuthorities() {
        final DeferredResult <ResponseEntity < ? >> out = new DeferredResult <>((long)100000);
        log.info("Coming to callAUTH");
        ForkJoinPool.commonPool().submit(() -> {
            Simulation simulation = simulationRepository.findById((long) 1);
            double timer = simulation.getCallAuthoritiesTimer();
            double multiplier = simulation.getTimeMultiplier();
            timer = timer*60*1000/multiplier;
            System.out.println("timer" + timer);
            try {
                System.out.println("sleeping" + Math.round(timer));
                Thread.sleep(Math.round(timer));
            } catch (InterruptedException e) {
            }
            System.out.println("done sleeping");
            out.setResult(ResponseEntity.ok().build());
        });

        return out;
    }

    /**
     * Away mode lights times response entity.
     *
     * @param object the object
     * @return the response entity
     */
    @PostMapping("simulation/awaymode-lights-time")
    public ResponseEntity<?> awayModeLightsTimes(@RequestBody AwayModeTime object){
        securityService.awayModeLightsTimes(object);
        return ResponseEntity.ok().build();
    }
}
