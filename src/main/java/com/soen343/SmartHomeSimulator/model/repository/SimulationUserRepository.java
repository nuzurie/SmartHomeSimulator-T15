package com.soen343.SmartHomeSimulator.model.repository;

import com.soen343.SmartHomeSimulator.model.SimulationUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.HashSet;
import java.util.Set;

public interface SimulationUserRepository{

    public Set<SimulationUser> simulationUserSet = new HashSet<>();

    public SimulationUser findById(Long id);

    public SimulationUser save(SimulationUser simulationUser);

    public Set<SimulationUser> findAll();

    public SimulationUser remove(SimulationUser simulationUser);

    public SimulationUser deleteById(Long id);

}
