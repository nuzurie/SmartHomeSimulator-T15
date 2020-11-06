package com.soen343.SmartHomeSimulator.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SimulationUser implements Serializable {


    public static long classId = 0;
    public long id;
    public String name;
    public String privilege;

    public void setId() {
        this.id = ++classId;
    }

}