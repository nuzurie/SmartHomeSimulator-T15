package com.soen343.SmartHomeSimulator.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Sensor {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String type;
    private String value;

    @ManyToOne(cascade=CascadeType.PERSIST)
    private Room room;

    @ManyToOne(cascade=CascadeType.PERSIST)
    private Home home;
}
