package com.soen343.SmartHomeSimulator.service;

import com.soen343.SmartHomeSimulator.module.security.controller.Security;
import com.soen343.SmartHomeSimulator.module.security.model.AwayModeTime;
import com.soen343.SmartHomeSimulator.module.simulation.model.Simulation;
import com.soen343.SmartHomeSimulator.module.simulation.repository.SimulationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class SecurityService {

    /**
     * The Simulation repository.
     */
    SimulationRepository simulationRepository;
    /**
     * The Security.
     */
    Security security;

    /**
     * Instantiates a new Security service.
     *
     * @param simulationRepository the simulation repository
     * @param security             the security
     */
    @Autowired
    public SecurityService(SimulationRepository simulationRepository, Security security) {
        this.simulationRepository = simulationRepository;
        this.security = security;
    }

    public void callAuthoritiesTimer(String timer){
        System.out.println(timer);
        double callTimer = Double.valueOf(timer);
        Simulation simulation = simulationRepository.findById((long) 1);
        simulation.setCallAuthoritiesTimer(callTimer);
    }

    public void awayModeLightsTimes(AwayModeTime amTime){
        System.out.println(amTime);
        Simulation simulation = simulationRepository.findById((long) 1);
        simulation.lightsTimeParse(amTime.getTime1(), amTime.getTime2());
    }
}
