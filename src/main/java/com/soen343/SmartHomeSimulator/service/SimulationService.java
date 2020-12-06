package com.soen343.SmartHomeSimulator.service;

import com.soen343.SmartHomeSimulator.model.repository.HomeRepository;
import com.soen343.SmartHomeSimulator.model.repository.SimulationUserRepository;
import com.soen343.SmartHomeSimulator.module.simulation.model.Simulation;
import com.soen343.SmartHomeSimulator.module.simulation.repository.SimulationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
@Slf4j
@Service
public class SimulationService {

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
     * Instantiates a new Simuation service.
     *
     * @param homeRepository           the home repository
     * @param simulationRepository     the simulation repository
     * @param simulationUserRepository the simulation user repository
     */
    @Autowired
    public SimulationService(HomeRepository homeRepository, SimulationRepository simulationRepository, SimulationUserRepository simulationUserRepository) {
        this.homeRepository = homeRepository;
        this.simulationRepository = simulationRepository;
        this.simulationUserRepository = simulationUserRepository;
    }

    public Simulation updateSimulation(Simulation simulation){
        Simulation currentSimulation = simulationRepository.findById((long)1);
        currentSimulation.setDate(simulation.getDate());
        currentSimulation.setTime(simulation.getTime());
        currentSimulation.setTemperature(simulation.getTemperature());

        return currentSimulation;
    }

    public Simulation getSimulation(){
        Simulation simulation = simulationRepository.findById((long)1);
        if (simulation == null){
            simulation = simulationRepository.findById((long)1);
        }
        log.info("After getting the simulation is {}", simulationRepository.findAll());
        System.out.println(simulation);
        return simulation;
    }

    public void deleteSimulation(Simulation simulation){
        simulationRepository.deleteById(simulation.getId());
        System.out.println(simulationRepository.findAll());
    }
}
