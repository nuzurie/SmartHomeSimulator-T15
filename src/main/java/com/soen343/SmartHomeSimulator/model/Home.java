package com.soen343.SmartHomeSimulator.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@Entity
@Builder
public class Home {

    private long classId = 0;
//    @Id
//    @GeneratedValue
    private long id;
    private String name;
    private int temperature;

//    @ManyToOne(cascade=CascadeType.PERSIST)
//    private User user;

//    @OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @Builder.Default
    private Set<Room> rooms = new HashSet<>();

//    @OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
//    private Set<Sensor> sensors;

    public void setId() {
        this.id = ++classId;
    }

    public void deleteUser(SimulationUser simulationUser){
        Set<Room> rooms = this.getRooms();
        for (Room room:
             rooms) {
            room.deleteUser(simulationUser);
        }
    }

}
