package com.soen343.SmartHomeSimulator.module.simulation.model;

import com.soen343.SmartHomeSimulator.model.Home;
import com.soen343.SmartHomeSimulator.model.SimulationUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Simulation {

    @Id
    public String name;
    private double temperature;
    private String date;
    private String time;

    @OneToOne
    private Home home;

    @OneToMany
    private Set<SimulationUser> simulationUsers;

}
