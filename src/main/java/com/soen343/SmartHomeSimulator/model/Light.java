package com.soen343.SmartHomeSimulator.model;

import lombok.*;

@Data
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
public class Light implements setName{

    private static long classId = 0;
    @Builder.Default
    @EqualsAndHashCode.Include
    private long id = ++classId;
    private boolean turnedOn;
    private String name;

    public void turnOn(){
        this.turnedOn = true;
    }

    public void turnOff(){
        this.turnedOn = false;
    }
}