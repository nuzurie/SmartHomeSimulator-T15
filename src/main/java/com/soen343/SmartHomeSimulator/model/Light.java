package com.soen343.SmartHomeSimulator.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Light{


    private static long classId = 0;
    @Builder.Default
    private long id = ++classId;
    private boolean turnedOn;

    public void turnOn(){
        this.turnedOn = true;
    }

    public void turnOff(){
        this.turnedOn = false;
    }
}