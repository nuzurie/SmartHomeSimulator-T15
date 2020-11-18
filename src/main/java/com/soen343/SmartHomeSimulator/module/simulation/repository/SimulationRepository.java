package com.soen343.SmartHomeSimulator.module.simulation.repository;

import com.soen343.SmartHomeSimulator.module.simulation.model.Simulation;

import java.util.HashSet;
import java.util.Set;

/**
 * The Simulation repository Interface.
 */
public interface SimulationRepository{

    /**
     * The Set of Simulations.
     */
    public Set<Simulation> simulationSet = new HashSet<>();

    /**
     * Find a Simulation by ID.
     *
     * @param id the id
     * @return the simulation
     */
    public Simulation findById(Long id);

    /**
     * Save simulation.
     *
     * @param simulation the simulation
     * @return the simulation
     */
    public Simulation save(Simulation simulation);

    /**
     * Get Set of all Simulations.
     *
     * @return the set
     */
    public Set<Simulation> findAll();

    /**
     * Remove Simulation.
     *
     * @param simulation the simulation
     * @return the simulation
     */
    public Simulation remove(Simulation simulation);

    /**
     * Delete Simulation by ID.
     *
     * @param id the id
     * @return the simulation
     */
    public Simulation deleteById(Long id);

}