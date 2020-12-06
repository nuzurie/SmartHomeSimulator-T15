package com.soen343.SmartHomeSimulator.service;

import com.soen343.SmartHomeSimulator.model.SimulationUser;
import com.soen343.SmartHomeSimulator.model.repository.SimulationUserRepository;
import com.soen343.SmartHomeSimulator.module.simulation.model.Simulation;
import com.soen343.SmartHomeSimulator.module.simulation.repository.SimulationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
public class SimulationUserService {

    private SimulationUserRepository simulationUserRepository;
    private SimulationRepository simulationRepository;

    /**
     * Instantiates a new SimulationUserService.
     *
     * @param simulationUserRepository the simulation user repository
     * @param simulationRepository     the simulation repository
     */
    public SimulationUserService(SimulationUserRepository simulationUserRepository, SimulationRepository simulationRepository) {
        this.simulationUserRepository = simulationUserRepository;
        this.simulationRepository = simulationRepository;
    }

    public SimulationUser createNewUser(SimulationUser user){
        user.setId();
        SimulationUser created_user = simulationUserRepository.save(user);
        simulationRepository.findById(Long.valueOf(1)).getSimulationUsers().add(created_user);
        return created_user;
    }

    public SimulationUser updateUser(SimulationUser user, Long id){
        SimulationUser simulationUser = simulationUserRepository.findById(id);

        simulationUser.setName(user.getName());
        simulationUser.setPrivilege(user.getPrivilege());
        return simulationUser;
    }

    public void deleteUser(Long id){
        SimulationUser simulationUser = simulationUserRepository.findById(id);
        simulationUserRepository.deleteById(id);
        Simulation simulation = simulationRepository.findById(Long.valueOf(1));
        simulation.deleteUser(simulationUser);
    }

    public void saveUsers(){
        Set<SimulationUser> simulationUsers = simulationUserRepository.findAll();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream("users.txt"));
            for (SimulationUser user : simulationUsers
            ) {
                oos.writeObject(user);
            }
            oos.close();
        } catch (IOException e) {
            log.info(e.getMessage());
        }

        loadUsers();
    }

    public Set<SimulationUser> loadUsers(){

        ObjectInputStream ois = null;
        Set<SimulationUser> users = new HashSet<>();
        SimulationUser a = SimulationUser.builder().name("Sunny").privilege("Parent").build();
        try {
            ois = new ObjectInputStream(new FileInputStream("users.txt"));
            while (true){
                SimulationUser user = (SimulationUser) ois.readObject();
                users.add(user);
            }
        }
        catch (EOFException e){
            log.info("Reached end of file.");
        }
        catch (IOException e){
            log.info(e.getMessage());
        }
        catch (ClassNotFoundException e){
            log.info(e.getMessage());
        }

        users.forEach(user -> {
            SimulationUser created_user = simulationUserRepository.save(user);
            simulationRepository.findById(Long.valueOf(1)).getSimulationUsers().add(created_user);
        });

        return users;
    }
}
