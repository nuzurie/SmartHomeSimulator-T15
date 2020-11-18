package com.soen343.SmartHomeSimulator.model;

import lombok.*;

/**
 * The Window object.
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Window implements setName{

    private static long classId = 0;
    @Builder.Default
    @EqualsAndHashCode.Include
    private long id = ++classId;
    private boolean open;
    private boolean blocked;
    private String name;

    /**
     * Block window.
     */
    public void blockWindow(){
        this.blocked = true;
    }

    /**
     * Un block window.
     */
    public void unBlockWindow(){
        this.blocked = false;
    }

    /**
     * Open window.
     */
    public void openWindow(){
        this.open = true;
    }

    /**
     * Close window.
     */
    public void closeWindow(){
        this.open = false;
    }

}
