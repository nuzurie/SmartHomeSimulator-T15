package com.soen343.SmartHomeSimulator.module.simulation.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.soen343.SmartHomeSimulator.model.*;

import com.soen343.SmartHomeSimulator.module.security.controller.Security;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;

import java.util.Set;

@Data
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class Simulation  implements Subject{

    public long name;
    private double temperature = 22.5;
    private boolean lightsAutoMode;
    private String date = "01-01-1980";
    private String time = "12:00";
    private Home home = Home.builder().build();
    private Set<SimulationUser> simulationUsers = new HashSet<>();
    private SimulationUser loggedInUser;
    private boolean awayMode;
    private Observer observer;

    @Autowired
    public Simulation(Long name, double temperature, boolean lightsAutoMode, String date, String time, Home home, Set<SimulationUser> simulationUsers, SimulationUser loggedInUser, boolean awayMode, Observer observer) {
        this.name = name;
        this.temperature = temperature;
        this.date = date;
        this.time = time;
        this.home = home;
        this.lightsAutoMode = lightsAutoMode;
        this.simulationUsers = simulationUsers;
        this.loggedInUser = loggedInUser;
        this.awayMode = awayMode;
        this.observer = observer;
    }

    public long getId(){
        return this.getName();
    }

    public void deleteUser(SimulationUser simulationUser){
        this.getSimulationUsers().remove(simulationUser);
        this.getHome().deleteUser(simulationUser);
    }

    public void setAutoMode(){
        if (lightsAutoMode){
            List<Room> rooms = this.home.getRooms();
            rooms.forEach(room ->{
                List<Light> lights = room.getLights();
                if (room.getSimulationUsers().isEmpty()){
                    lights.forEach(Light::turnOff);
                }
                else {
                    lights.forEach(Light::turnOn);
                }
            });
        }
    }

    @JsonIgnore
    public Observer getObserver(){
        return this.observer;
    }

    @Override
    public int notifyObserver() {
        if (!this.awayMode)
            return 0;
        //if awayMode is active, return what is returned from observer (which checks if rooms have anyone in)
        return observer.update(this.home.getRooms());
    }

    public int toggleAwayMode(){
        if (this.awayMode) {
            this.awayMode = false;
            return 0;
        }
        // if awayMode is off, first check if rooms are empty
        if (observer.update(this.home.getRooms())==-1)
            return -1;

        System.out.println("coming here");
        this.awayMode = true;
        return 0;
    }

}
