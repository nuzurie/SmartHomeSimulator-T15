package com.soen343.SmartHomeSimulator.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Room {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String size;
    private int temperature;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<DoorWindow> window;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<DoorWindow> door;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Lights> lights;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Set<SimulationUser> simulationUsers;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Sensor> sensors;


    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Entity
    @Builder
    public static class DoorWindow {

        @Id
        @GeneratedValue
        private Long id;
        private boolean open;
        private boolean blocked;

    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Entity
    @Builder
    public static class Lights {

        @Id
        @GeneratedValue
        private Long id;
        private boolean turnedOn;

    }

}
