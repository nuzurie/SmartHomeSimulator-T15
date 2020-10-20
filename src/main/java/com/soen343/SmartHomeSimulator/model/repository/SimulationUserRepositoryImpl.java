package com.soen343.SmartHomeSimulator.model.repository;

import com.soen343.SmartHomeSimulator.model.SimulationUser;
import com.soen343.SmartHomeSimulator.module.simulation.model.Simulation;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class SimulationUserRepositoryImpl implements SimulationUserRepository {
    @Override
    public SimulationUser findById(Long id) {
        for (SimulationUser user:
             this.simulationUserSet) {
            if (user.getId()==id)
                return user;
        }
        return null;
    }

    @Override
    public SimulationUser save(SimulationUser simulationUser) {
        SimulationUser already_exists = findById(simulationUser.getId());
        remove(already_exists);
        this.simulationUserSet.add(simulationUser);
        return this.findById(simulationUser.getId());
    }

    @Override
    public Set<SimulationUser> findAll() {
        return simulationUserSet;
    }

    @Override
    public SimulationUser remove(SimulationUser simulationUser) {
        Boolean removed = this.simulationUserSet.remove(simulationUser);
        if (removed)
            return simulationUser;
        else
            return null;
    }

    @Override
    public SimulationUser deleteById(Long id) {
        SimulationUser user = this.findById(id);
        return remove(user);
    }
}
