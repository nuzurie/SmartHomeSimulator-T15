package com.soen343.SmartHomeSimulator.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Home{

    private long classId = 0;
    private long id;
    private String name;
    private int temperature;
    @Builder.Default
    private List<Room> rooms = new LinkedList<>();
    //    private Set<Sensor> sensors;
    @Builder.Default
    private Light backyardLight = Light.builder().name("Backyard").build();
    @Builder.Default
    private Light entranceLight = Light.builder().name("Entrance").build();
    @Builder.Default
    private Door backyardDoor = Door.builder().name("Backyard").build();
    @Builder.Default
    private Door entranceDoor = Door.builder().name("Entrance").build();

    public void setId() {
        this.id = ++classId;
    }

    public void deleteUser(SimulationUser simulationUser) {
        List<Room> rooms = this.getRooms();
        for (Room room :
                rooms) {
            room.deleteUser(simulationUser);
        }
    }
}
