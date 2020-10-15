package com.soen343.SmartHomeSimulator.model.repository;

import com.soen343.SmartHomeSimulator.model.SimulationUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SimulationUserRepository extends JpaRepository<SimulationUser, Long> {
}
