package com.soen343.SmartHomeSimulator.service;

import com.soen343.SmartHomeSimulator.model.Light;
import com.soen343.SmartHomeSimulator.model.SimulationUser;
import com.soen343.SmartHomeSimulator.model.repository.RepositoryService;
import com.soen343.SmartHomeSimulator.model.repository.SimulationUserRepository;
import com.soen343.SmartHomeSimulator.module.security.model.AwayModeLights;
import com.soen343.SmartHomeSimulator.module.simulation.model.Simulation;
import com.soen343.SmartHomeSimulator.module.simulation.repository.SimulationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CoreService {

    private SimulationRepository simulationRepository;
    private SimulationUserRepository simulationUserRepository;
    private RepositoryService repositoryService;

    /**
     * Instantiates a new Core service.
     *
     * @param simulationRepository     the simulation repository
     * @param simulationUserRepository the simulation user repository
     * @param repositoryService        the repository service
     */
    @Autowired
    public CoreService(SimulationRepository simulationRepository, SimulationUserRepository simulationUserRepository, RepositoryService repositoryService) {
        this.simulationRepository = simulationRepository;
        this.simulationUserRepository = simulationUserRepository;
        this.repositoryService = repositoryService;
    }

    public SimulationUser updateUserLogin(long id){
        SimulationUser newUserLogin = simulationUserRepository.findById(id);
        Simulation simulation = simulationRepository.findById((long) 1);
        simulation.setLoggedInUser(newUserLogin);
        return newUserLogin;
    }


    public Light toggleLights(Light light){
        light.setTurnedOn(!light.isTurnedOn());
        Light newLight = this.repositoryService.saveLight(light);
        return newLight;
    }

    public void removeUser(Long roomID, SimulationUser simulationUser){
        Simulation currentSimulation = simulationRepository.findById((long) 1);
        repositoryService.removeUser(roomID, simulationUser);
        currentSimulation.setAutoMode();
    }

    public void setAwayModeLights(AwayModeLights lights){
        System.out.println(lights);
        Simulation simulation = simulationRepository.findById((long) 1);
        List<Light> lightList = repositoryService.getLightsById(lights.getChecked());
        simulation.setChosenAwayModeLights(lightList);
    }
}
