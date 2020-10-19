package com.soen343.SmartHomeSimulator.module.simulation.repository;

import com.soen343.SmartHomeSimulator.model.SimulationUser;
import com.soen343.SmartHomeSimulator.module.simulation.model.Simulation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

public interface SimulationRepository{

    public Set<Simulation> simulationSet = new HashSet<>();

    public Simulation findById(Long id);

    public Simulation save(Simulation simulation);

    public Set<Simulation> findAll();

    public Simulation remove(Simulation simulation);

    public Simulation deleteById(Long id);

}