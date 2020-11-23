package com.soen343.SmartHomeSimulator.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * The Simulation user object.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SimulationUser implements Serializable {


    /**
     * The constant classId.
     */
    public static long classId = 0;
    /**
     * The Id.
     */
    public long id;
    /**
     * The Name.
     */
    public String name;
    /**
     * The Privilege.
     */
    public String privilege;

    /**
     * Sets id.
     */
    public void setId() {
        this.id = ++classId;
    }

}