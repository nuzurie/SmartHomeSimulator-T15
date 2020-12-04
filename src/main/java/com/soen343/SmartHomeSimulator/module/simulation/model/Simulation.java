package com.soen343.SmartHomeSimulator.module.simulation.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.soen343.SmartHomeSimulator.model.*;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Simulation object.
 */
@Data
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class Simulation implements Subject {

    public long name;
    private double temperature = 22.5;
    private boolean lightsAutoMode;
    private String date = "1980-01-01";
    private String time = "12:00";
    private Home home = Home.builder().build();
    private Set<SimulationUser> simulationUsers = new HashSet<>();
    private SimulationUser loggedInUser;
    private boolean awayMode;
    private Observer observer;
    @Builder.Default
    private double timeMultiplier = 1;
    @Builder.Default
    private double callAuthoritiesTimer = 1;
    private LocalDateTime dateTime;

    private LocalTime lightsTimeStart;
    private LocalTime lightsTimeEnd;
    private List<Light> chosenAwayModeLights;


    /**
     * Instantiates a new Simulation.
     *
     * @param name                 the name
     * @param temperature          the temperature
     * @param lightsAutoMode       the lights auto mode
     * @param date                 the date
     * @param time                 the time
     * @param home                 the home
     * @param simulationUsers      the simulation users
     * @param loggedInUser         the logged in user
     * @param awayMode             the away mode
     * @param observer             the observer
     * @param callAuthoritiesTimer the call authorities timer
     * @param timeMultiplier       the time multiplier
     * @param dateTime             the date time
     * @param lightsTimeStart      the lights time start
     * @param lightsTimeEnd        the lights time end
     * @param lights               the lights
     */
    @Autowired
    public Simulation(Long name, double temperature, boolean lightsAutoMode, String date, String time, Home home, Set<SimulationUser> simulationUsers, SimulationUser loggedInUser, boolean awayMode, Observer observer, double callAuthoritiesTimer, double timeMultiplier, LocalDateTime dateTime, LocalTime lightsTimeStart, LocalTime lightsTimeEnd, List<Light> lights) {
        this.name = name;
        this.temperature = temperature;
        this.date = date;
        this.time = time;
        this.home = home;
        this.lightsAutoMode = lightsAutoMode;
        this.simulationUsers = simulationUsers;
        this.loggedInUser = loggedInUser;
        this.callAuthoritiesTimer = callAuthoritiesTimer;
        this.awayMode = awayMode;
        this.timeMultiplier = timeMultiplier;
        this.dateTime = dateTime;
        this.observer = observer;
        this.lightsTimeStart = lightsTimeStart;
        this.lightsTimeEnd = lightsTimeEnd;
        this.chosenAwayModeLights = lights;
    }

    /**
     * Gets Simulation Id.
     *
     * @return the id
     */
    public long getId() {
        return this.getName();
    }

    /**
     * Delete User.
     *
     * @param simulationUser the simulation user
     */
    public void deleteUser(SimulationUser simulationUser) {
        this.getSimulationUsers().remove(simulationUser);
        this.getHome().deleteUser(simulationUser);
    }

    /**
     * Sets auto mode.
     */
    public void setAutoMode() {
        if (lightsAutoMode) {
            List<Room> rooms = this.home.getRooms();
            rooms.forEach(room -> {
                List<Light> lights = room.getLights();
                if (room.getSimulationUsers().isEmpty()) {
                    lights.forEach(Light::turnOff);
                } else {
                    lights.forEach(Light::turnOn);
                }
            });
            //Set timer for some lights
        }
    }

    /**
     * Gets observer.
     *
     * @return the observer
     */
    @JsonIgnore
    public Observer getObserver() {
        return this.observer;
    }

    /**
     * Gets date time.
     *
     * @return the date time
     */
    @JsonIgnore
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    @Override
    public int notifyObserver() {
        if (!this.awayMode)
            return 0;
        //if awayMode is active, return what is returned from observer (which checks if rooms have anyone in)
        return observer.update(this.home.getRooms());
    }

    /**
     * Toggle away mode.
     *
     * @return the int
     */
    public int toggleAwayMode() {
        if (this.awayMode) {
            this.awayMode = false;
            return 0;
        }
        // if awayMode is off, first check if rooms are empty
        if (observer.update(this.home.getRooms()) == -1)
            return -1;

        System.out.println("coming here");
        this.awayMode = true;
        return 0;
    }

    /**
     * Parse date.
     */
    public void parseDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String dateTimeString = this.date + " " + this.time;
        this.dateTime = LocalDateTime.parse(dateTimeString, formatter);
    }

    /**
     * Increase time.
     */
    public void increaseTime() {
        while (true) {
            long s = Math.round(this.timeMultiplier);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
            }
            parseDate();
//            setTimerLights();
            this.dateTime = this.dateTime.plusSeconds(s);
            updateDateTimeStrings();
        }
    }

    /**
     * Update date time strings.
     */
    public void updateDateTimeStrings() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        this.date = this.dateTime.format(formatter);
        formatter = DateTimeFormatter.ofPattern("HH:mm");
        this.time = this.dateTime.format(formatter);
    }

    /**
     * Lights time parse.
     *
     * @param timeStart the time start
     * @param timeEnd   the time end
     */
    public void lightsTimeParse(String timeStart, String timeEnd) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        this.lightsTimeStart = LocalTime.parse(timeStart, formatter);
        this.lightsTimeEnd = LocalTime.parse(timeEnd, formatter);
        System.out.println(this.lightsTimeStart + " " + this.lightsTimeEnd);
        setTimerLights();
    }

    /**
     * Sets timer lights.
     */
    public void setTimerLights() {
        Light a = this.home.getBackyardLight();
        if (this.awayMode) {
            if (this.lightsTimeEnd != null && this.lightsTimeStart != null) {
                System.out.println(this.lightsTimeStart.compareTo(this.lightsTimeEnd));
                System.out.println(this.lightsTimeEnd.compareTo(this.lightsTimeStart));
                System.out.println(this.dateTime.getHour() + " " + this.dateTime.getMinute());
            }
        }
    }
}

