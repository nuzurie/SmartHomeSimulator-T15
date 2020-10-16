package com.soen343.SmartHomeSimulator.module.simulation.repository;

import com.soen343.SmartHomeSimulator.module.simulation.model.Simulation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimulationRepository extends JpaRepository<Simulation, String> {
}