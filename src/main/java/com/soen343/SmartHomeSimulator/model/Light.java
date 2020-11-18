package com.soen343.SmartHomeSimulator.model;

import lombok.*;

/**
 * The Light object.
 */
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

    /**
     * Turn on Light.
     */
    public void turnOn(){
        this.turnedOn = true;
    }

    /**
     * Turn off Light.
     */
    public void turnOff(){
        this.turnedOn = false;
    }
}