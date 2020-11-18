package com.soen343.SmartHomeSimulator.model.repository;

import com.soen343.SmartHomeSimulator.model.*;
import com.soen343.SmartHomeSimulator.module.simulation.model.Simulation;
import com.soen343.SmartHomeSimulator.module.simulation.repository.SimulationRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

/**
 * The Repository service Object.
 */
@Getter
@Setter
@Service
@NoArgsConstructor
public class RepositoryService {
    /**
     * The Simulation repository.
     */
    SimulationRepository simulationRepository;
    /**
     * The Light repository.
     */
    LightRepository lightRepository;
    /**
     * The Window repository.
     */
    WindowRepository windowRepository;
    /**
     * The Door repository.
     */
    DoorRepository doorRepository;
    /**
     * The Room repository.
     */
    RoomRepository roomRepository;

    /**
     * Instantiates a new Repository service.
     *
     * @param simulationRepository the simulation repository
     * @param lightRepository      the light repository
     * @param windowRepository     the window repository
     * @param doorRepository       the door repository
     * @param roomRepository       the room repository
     */
    @Autowired
    public RepositoryService(SimulationRepository simulationRepository, LightRepository lightRepository, WindowRepository windowRepository, DoorRepository doorRepository, RoomRepository roomRepository) {
        this.simulationRepository = simulationRepository;
        this.lightRepository = lightRepository;
        this.windowRepository = windowRepository;
        this.doorRepository = doorRepository;
        this.roomRepository = roomRepository;
    }

    /**
     * Gets lights for user.
     *
     * @return the lights for user
     */
    public List<Light> getLightsForUser() {
        Simulation simulation = simulationRepository.findById((long) 1);
        Home home = simulation.getHome();
        SimulationUser user = simulation.getLoggedInUser();
        if (user.getPrivilege().equalsIgnoreCase("stranger"))
            return new LinkedList<Light>();
        List<Room> rooms = home.getRooms();

        if (user.getPrivilege().equalsIgnoreCase("parent")) {
            return lightRepository.findAll();
        } else {
            List<Light> lights = new LinkedList<>();
            rooms.forEach(room -> {
                if (room.getSimulationUsers().contains(user)) {
                    lights.addAll(room.getLights());
                }
            });
            return lights;
        }
    }

    /**
     * Save lights.
     */
    public void saveLights() {
        Home home = simulationRepository.findById((long) 1).getHome();
        List<Room> rooms = home.getRooms();

        List<Light> lights = new LinkedList<>();
        lights.add(home.getBackyardLight());
        lights.add(home.getEntranceLight());
        rooms.forEach(room -> {
            List<Light> lightList = room.getLights();
            lights.addAll(lightList);
        });

        lights.forEach(lightRepository::save);
    }

    /**
     * Save light.
     *
     * @param light the light
     * @return the light
     */
    public Light saveLight(Light light) {
        return this.lightRepository.save(light);
    }

    /**
     * Save window.
     *
     * @param window the window
     * @return the window
     */
    public Window saveWindow(Window window) {
        return this.windowRepository.save(window);
    }

    /**
     * Save door.
     *
     * @param door the door
     * @return the door
     */
    public Door saveDoor(Door door) {
        return this.doorRepository.save(door);
    }

    /**
     * Gets windows for user.
     *
     * @return the windows for user
     */
    public List<Window> getWindowsForUser() {
        Simulation simulation = simulationRepository.findById((long) 1);
        Home home = simulation.getHome();
        SimulationUser user = simulation.getLoggedInUser();
        if (user.getPrivilege().equalsIgnoreCase("stranger"))
            return new LinkedList<Window>();
        List<Room> rooms = home.getRooms();

        if (user.getPrivilege().equalsIgnoreCase("parent")) {
            return windowRepository.findAll();
        } else {
            List<Window> windows = new LinkedList<>();
            rooms.forEach(room -> {
                if (room.getSimulationUsers().contains(user)) {
                    windows.addAll(room.getWindows());
                }
            });
            return windows;
        }
    }

    /**
     * Save windows.
     */
    public void saveWindows() {
        Home home = simulationRepository.findById((long) 1).getHome();
        List<Room> rooms = home.getRooms();

        List<Window> windowList = new LinkedList<>();
        rooms.forEach(room -> {
            List<Window> windows = room.getWindows();
            windowList.addAll(windows);
        });

        windowList.forEach(windowRepository::save);
    }

    /**
     * Gets doors for user.
     *
     * @return the doors for user
     */
    public List<Door> getDoorsForUser() {
        Simulation simulation = simulationRepository.findById((long) 1);
        Home home = simulation.getHome();
        SimulationUser user = simulation.getLoggedInUser();
        if (user.getPrivilege().equalsIgnoreCase("stranger"))
            return new LinkedList<Door>();
        List<Room> rooms = home.getRooms();

        if (user.getPrivilege().equalsIgnoreCase("parent")) {
            return doorRepository.findAll();
        } else {
            List<Door> doors = new LinkedList<>();
            rooms.forEach(room -> {
                if (room.getSimulationUsers().contains(user)) {
                    doors.addAll(room.getDoors());
                }
            });
            return doors;
        }
    }

    /**
     * Save doors.
     */
    public void saveDoors() {
        Home home = simulationRepository.findById((long) 1).getHome();
        List<Room> rooms = home.getRooms();

        List<Door> doors = new LinkedList<>();
        doors.add(home.getBackyardDoor());
        doors.add(home.getEntranceDoor());
        rooms.forEach(room -> {
            List<Door> doorList = room.getDoors();
            doors.addAll(doorList);
        });

        doors.forEach(doorRepository::save);
    }

    /**
     * Add user.
     *
     * @param roomID the room id
     * @param user   the user
     */
    public void addUser(Long roomID, SimulationUser user) {
        Simulation simulation = simulationRepository.findById((long) 1);
        //Remove from simulaiton
        SimulationUser simulationUser = null;
        Set<SimulationUser> simulationUserSet = simulation.getSimulationUsers();
        for (SimulationUser simulationUser1 : simulationUserSet
        ) {
            if (simulationUser1.id == user.id) {
                simulationUser = simulationUser1;
            }
        }
        simulation.getSimulationUsers().remove(simulationUser);
        //add to room
        List<Room> roomList = simulation.getHome().getRooms();
        for (Room room : roomList
        ) {
            if (room.getId() == roomID) {
                if (simulationUser != null)
                room.getSimulationUsers().add(simulationUser);
            }
        }
    }

    /**
     * Remove user.
     *
     * @param roomID the room id
     * @param user   the user
     */
    public void removeUser(Long roomID, SimulationUser user) {
        Simulation simulation = simulationRepository.findById((long) 1);
        //find the user
        SimulationUser simulationUser = null;
        List<Room> roomList = simulation.getHome().getRooms();
        for (Room room : roomList
        ) {
            if (room.getId() == roomID) {
                List<SimulationUser> simulationUserList = room.getSimulationUsers();
                for (SimulationUser simulationUser1 : simulationUserList
                ) {
                    if (simulationUser1.id == user.id) {
                        simulationUser = simulationUser1;
                        room.getSimulationUsers().remove(simulationUser1);
                    }
                }
            }
        }
        if (simulationUser != null)
            simulation.getSimulationUsers().add(simulationUser);
    }

    /**
     * Get list of lights by ID of AwayMode light.
     *
     * @param id the AwayMode light id
     * @return the list
     */
    public List<Light> getLightsById(long[] id){
        List<Light> lightList = new LinkedList<>();
        for (long i: id
             ) {
            Light light = lightRepository.findById(i);
            if (light!=null){
                lightList.add(light);
            }
        }

        return lightList;
    }
}
