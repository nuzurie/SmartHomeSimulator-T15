package com.soen343.SmartHomeSimulator.module.heating.model;

import com.soen343.SmartHomeSimulator.config.SpringContext;
import com.soen343.SmartHomeSimulator.model.Room;
import com.soen343.SmartHomeSimulator.module.simulation.model.Simulation;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Builder
@Slf4j
@Data
@Getter
@Setter
public class Heating {
    int id;
    @Builder.Default
    private ZoneAndTimeNumbers zoneAndTimeNumbers = new ZoneAndTimeNumbers(1, 1);
    @Builder.Default
    private List<Zone> zones = new LinkedList<>();
    @Builder.Default
    private double defaultTemperature = 22;
    @Builder.Default
    private Set<Room> overriddenRooms = new HashSet<>();
    private MONTHS[] summer;
    private MONTHS[] winter;
    private double summerTemperature = 22;
    private double winterTemperature = 14;
    private boolean HVACon;

    private enum MONTHS {
        JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE, JULY, AUGUST, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER
    }

    public MONTHS[] getMonths(){
        return MONTHS.values();
    }
}
