package com.soen343.SmartHomeSimulator.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.GenerationType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@Entity
public class SimulationUser {


    public static long classId = 0;
//    @Id
//    @GeneratedValue
    public long id;
    public String name;
    public String privilege;

    public void setId() {
        this.id = ++classId;
    }

}