package com.soen343.SmartHomeSimulator.model;

import com.soen343.SmartHomeSimulator.model.repository.SimulationUserRepository;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@Data
@NoArgsConstructor
@Builder
public class Room {

    private static long classId = 0;
    @Builder.Default
    private Long id = ++classId;
    private String name;
    private String size;
    @Builder.Default
    private double temperature = 22.5;
    @Builder.Default
    private List<Window> windows = new LinkedList<>();
    @Builder.Default
    private List<Door> doors = new LinkedList<>();
    @Builder.Default
    private List<Light> lights = new LinkedList<>();
    @Builder.Default
    private List<SimulationUser> simulationUsers = new LinkedList<>();
    @Autowired
    private SimulationUserRepository simulationUserRepository;

    public void deleteUser(SimulationUser simulationUser) {
        this.simulationUsers.remove(simulationUser);
    }
//    private Set<Sensor> sensors;

    // == CONSTRUCTORS ==

    @Autowired
    public Room(Long id, String name, String size, double temperature, List<Window> windows, List<Door> doors, List<Light> lights, List<SimulationUser> simulationUsers, SimulationUserRepository simulationUserRepository) {
        this.id = ++classId;
        this.name = name;
        this.size = size;
        this.temperature = temperature;
        this.windows = windows;
        this.doors = doors;
        this.lights = lights;
        this.simulationUsers = simulationUsers;
        this.simulationUserRepository = simulationUserRepository;
    }

    // == METHODS ==

    public <T> void add(T t) {
        List list = null;

        if (t instanceof Door)
            list = this.doors;
        else if (t instanceof Window)
            list = this.windows;
        else if (t instanceof Light)
            list = this.lights;
        else if (t instanceof SimulationUser) {
            list = this.simulationUsers;
            simulationUserRepository.save((SimulationUser) t);
        }

        //if already exists, do nothing.
        if (list.contains(t))
            return;
        //otherwise add
        list.add(t);
    }
}
