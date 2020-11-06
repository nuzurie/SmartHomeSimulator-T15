package com.soen343.SmartHomeSimulator.model;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Door{

    private static long classId = 0;
    @Builder.Default
    @EqualsAndHashCode.Include
    private long id = ++classId;
    private boolean open;
    private boolean locked;

    public void lockDoor(){
        this.locked = true;
    }
    public void unlockDoor(){
        this.locked = false;
    }

    public void openDoor(){
        this.open = true;
    }
    public void closeDoor(){
        this.open = false;
    }

}
