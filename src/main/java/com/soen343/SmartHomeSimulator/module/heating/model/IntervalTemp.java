package com.soen343.SmartHomeSimulator.module.heating.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalTime;

@Builder
@Data
public class IntervalTemp {
    private LocalTime startTime;
    private LocalTime endTime;
    private double temperature;

    public IntervalTemp clone(){
        return IntervalTemp.builder().startTime(startTime).endTime(endTime).temperature(temperature).build();
    }
}
