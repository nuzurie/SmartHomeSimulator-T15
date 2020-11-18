package com.soen343.SmartHomeSimulator.model.repository;

import com.soen343.SmartHomeSimulator.model.SimulationUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.HashSet;
import java.util.Set;

/**
 * The interface SimulationUserRepository.
 */
public interface SimulationUserRepository{

    /**
     * The Set of Users in the Simulation.
     */
    public Set<SimulationUser> simulationUserSet = new HashSet<>();

    /**
     * Find User by ID.
     *
     * @param id the id
     * @return the simulation user
     */
    public SimulationUser findById(Long id);

    /**
     * Save simulation user.
     *
     * @param simulationUser the simulation user
     * @return the simulation user
     */
    public SimulationUser save(SimulationUser simulationUser);

    /**
     * Find all Users in the Simulation.
     *
     * @return the set
     */
    public Set<SimulationUser> findAll();

    /**
     * Remove simulation User.
     *
     * @param simulationUser the simulation user
     * @return the simulation user
     */
    public SimulationUser remove(SimulationUser simulationUser);

    /**
     * Delete User by ID in the Simulation.
     *
     * @param id the id
     * @return the simulation user
     */
    public SimulationUser deleteById(Long id);

}
