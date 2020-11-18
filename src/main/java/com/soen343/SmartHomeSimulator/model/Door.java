package com.soen343.SmartHomeSimulator.model;

import lombok.*;

/**
 * The Door object.
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Door implements setName{

    private static long classId = 0;
    @Builder.Default
    @EqualsAndHashCode.Include
    private long id = ++classId;
    private boolean open;
    private boolean locked;
    private String name;

    /**
     * Lock door.
     */
    public void lockDoor(){
        this.locked = true;
    }

    /**
     * Unlock door.
     */
    public void unlockDoor(){
        this.locked = false;
    }

    /**
     * Open door.
     */
    public void openDoor(){
        this.open = true;
    }

    /**
     * Close door.
     */
    public void closeDoor(){
        this.open = false;
    }

}
