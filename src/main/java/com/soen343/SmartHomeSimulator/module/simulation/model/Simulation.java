package com.soen343.SmartHomeSimulator.module.simulation.model;

import com.soen343.SmartHomeSimulator.model.Home;
import com.soen343.SmartHomeSimulator.model.SimulationUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
//@AllArgsConstructor
@Builder(toBuilder = true)
//@Entity
public class Simulation {

//    @Id
    public long name;
    private double temperature = 22.5;
    private String date = "01-01-1980";
    private String time = "12:00";

//    @OneToOne
    private Home home = Home.builder().build();

//    @OneToMany
    private Set<SimulationUser> simulationUsers = new HashSet<>();

    @Autowired
    public Simulation(Long name, double temperature, String date, String time, Home home, Set<SimulationUser> simulationUsers) {
        this.name = name;
        this.temperature = temperature;
        this.date = date;
        this.time = time;
        this.home = home;
        this.simulationUsers = simulationUsers;
    }

    public long getId(){
        return this.getName();
    }

    public void deleteUser(SimulationUser simulationUser){
        this.getSimulationUsers().remove(simulationUser);
        this.getHome().deleteUser(simulationUser);
    }
}
