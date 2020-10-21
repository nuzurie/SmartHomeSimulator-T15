package com.soen343.SmartHomeSimulator.model;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
//@AllArgsConstructor
@Builder(toBuilder = true)
//@Entity
public class Room {

    private static long classId = 0;
    //    @Id
//    @GeneratedValue
    @Builder.Default
    private Long id = ++classId;
    private String name;
    private String size;
    @Builder.Default
    private double temperature = 22.5;
    //    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Builder.Default
    private Set<DoorWindow> window = new HashSet<>();
//    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Builder.Default
    private Set<DoorWindow> door = new HashSet<>();
    //    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Builder.Default
    private Set<Lights> lights = new HashSet<>();
    //    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @Builder.Default
    private Set<SimulationUser> simulationUsers = new HashSet<>();

    public void deleteUser(SimulationUser simulationUser) {
        this.getSimulationUsers().remove(simulationUser);
    }
//    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    private Set<Sensor> sensors;


    @AllArgsConstructor
    @NoArgsConstructor
    @Data
//    @Entity
    @Builder
    public static class DoorWindow {

        private static long classId = 0;
        //        @Id
//        @GeneratedValue
        @Builder.Default
        private long id = ++classId;
        private boolean open;
        private boolean blocked;

        public boolean getBlocked(){
            return this.blocked;
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
//    @Entity
    @Builder
    public static class Lights {


        private static long classId = 0;
        //        @Id
//        @GeneratedValue
        @Builder.Default
        private long id = ++classId;
        private boolean turnedOn;

    }

    @Autowired
    public Room(Long id, String name, String size, double temperature, Set<DoorWindow> window, Set<DoorWindow> door, Set<Lights> lights, Set<SimulationUser> simulationUsers) {
        this.id = ++classId;
        this.name = name;
        this.size = size;
        this.temperature = temperature;
        this.window = window;
        this.door = door;
        this.lights = lights;
        this.simulationUsers = simulationUsers;
    }
}
