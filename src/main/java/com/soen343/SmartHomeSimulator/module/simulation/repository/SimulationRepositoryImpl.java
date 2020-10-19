package com.soen343.SmartHomeSimulator.module.simulation.repository;

import com.soen343.SmartHomeSimulator.model.SimulationUser;
import com.soen343.SmartHomeSimulator.module.simulation.model.Simulation;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class SimulationRepositoryImpl implements SimulationRepository{
    @Override
    public Simulation findById(Long id) {
        for (Simulation simulation:
                this.simulationSet) {
            if (simulation.getId()==id)
                return simulation;
        }
        return null;
    }

    @Override
    public Simulation save(Simulation simulation) {
        Simulation already_exists = findById(simulation.getId());
        remove(already_exists);
        this.simulationSet.add(simulation);
        return this.findById(simulation.getId());
    }

    @Override
    public Set<Simulation> findAll() {
        return simulationSet;
    }

    @Override
    public Simulation remove(Simulation simulation) {
        Boolean removed = this.simulationSet.remove(simulation);
        if (removed)
            return simulation;
        else
            return null;
    }

    @Override
    public Simulation deleteById(Long id) {
        Simulation user = this.findById(id);
        return remove(user);
    }
}

