package com.soen343.SmartHomeSimulator.module.simulation.model;

import com.soen343.SmartHomeSimulator.model.Home;
import com.soen343.SmartHomeSimulator.model.SimulationUser;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
//@AllArgsConstructor
@Builder(toBuilder = true)
//@Entity
public class Simulation {

    public long name;
    private double temperature = 22.5;
    private String date = "01-01-1980";
    private String time = "12:00";

    private Home home = Home.builder().build();

    private Set<SimulationUser> simulationUsers = new HashSet<>();

    private SimulationUser loggedInUser;

    @Autowired
    public Simulation(Long name, double temperature, String date, String time, Home home, Set<SimulationUser> simulationUsers, SimulationUser loggedInUser) {
        this.name = name;
        this.temperature = temperature;
        this.date = date;
        this.time = time;
        this.home = home;
        this.simulationUsers = simulationUsers;
        this.loggedInUser = loggedInUser;
    }

    public long getId(){
        return this.getName();
    }

    public void deleteUser(SimulationUser simulationUser){
        this.getSimulationUsers().remove(simulationUser);
        this.getHome().deleteUser(simulationUser);
    }
}
