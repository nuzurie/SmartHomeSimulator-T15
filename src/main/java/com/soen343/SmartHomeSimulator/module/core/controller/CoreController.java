package com.soen343.SmartHomeSimulator.module.core.controller;

import com.soen343.SmartHomeSimulator.config.SpringContext;
import com.soen343.SmartHomeSimulator.model.*;
import com.soen343.SmartHomeSimulator.model.repository.RepositoryService;
import com.soen343.SmartHomeSimulator.model.repository.SimulationUserRepository;
import com.soen343.SmartHomeSimulator.module.heating.model.HVAC;
import com.soen343.SmartHomeSimulator.module.security.model.AwayModeLights;
import com.soen343.SmartHomeSimulator.module.simulation.model.Simulation;
import com.soen343.SmartHomeSimulator.module.simulation.repository.SimulationRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * The Core controller.
 */
@Slf4j
@RestController
@RequestMapping("/api")
public class CoreController {

    private SimulationRepository simulationRepository;
    private SimulationUserRepository simulationUserRepository;
    private RepositoryService repositoryService;

    /**
     * Instantiates a new Core controller.
     *
     * @param simulationRepository     the simulation repository
     * @param simulationUserRepository the simulation user repository
     * @param repositoryService        the repository service
     */
    @Autowired
    public CoreController(SimulationRepository simulationRepository, SimulationUserRepository simulationUserRepository, RepositoryService repositoryService) {
        this.simulationRepository = simulationRepository;
        this.simulationUserRepository = simulationUserRepository;
        this.repositoryService = repositoryService;
    }

    /**
     * Update logged user response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @PutMapping("simulation/loginUser/{id}")
    public ResponseEntity<SimulationUser> updateLoginUser(@PathVariable long id) {
        SimulationUser newLoginUser = simulationUserRepository.findById(id);
        Simulation simulation = simulationRepository.findById((long) 1);
        simulation.setLoggedInUser(newLoginUser);

        return ResponseEntity.ok().body(newLoginUser);
    }

    /**
     * Toggle auto mode response entity.
     *
     * @return the response entity
     */
    @PutMapping("simulation/autoMode")
    public ResponseEntity<?> toggleAutoMode() {

        Simulation simulation = simulationRepository.findById((long) 1);
        if (simulation.getLoggedInUser().getPrivilege().equalsIgnoreCase("stranger"))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        simulation.setLightsAutoMode(!simulation.isLightsAutoMode());
        simulation.setAutoMode();

        return ResponseEntity.ok().build();
    }

    /**
     * Sets time multiplier.
     *
     * @param multipliers the multipliers
     * @return the time multiplier
     */
    @PutMapping("simulation/time-multiplier/{multipliers}")
    public ResponseEntity setTimeMultiplier(@PathVariable String multipliers) {
        double multiplier = Double.valueOf(multipliers);

        Simulation simulation = simulationRepository.findById((long) 1);
        if (simulation.getLoggedInUser().getPrivilege().equalsIgnoreCase("stranger"))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        simulation.setTimeMultiplier(multiplier);
        return ResponseEntity.ok().build();
    }

    /**
     * Get lights for user response entity.
     *
     * @return the response entity
     */
    @GetMapping("simulation/lights-for-user")
    public ResponseEntity<?> getLightsForUser(){
        return ResponseEntity.ok().body(repositoryService.getLightsForUser());
    }

    /**
     * Get windows for user response entity.
     *
     * @return the response entity
     */
    @GetMapping("simulation/windows-for-user")
    public ResponseEntity<?> getWindowsForUser(){
        return ResponseEntity.ok().body(repositoryService.getWindowsForUser());
    }

    /**
     * Get the rooms for user response entity.
     * @return the response entity
     */
    @GetMapping("simulation/rooms-for-user")
    public ResponseEntity<?> getRoomsForUser(){
        return ResponseEntity.ok().body(repositoryService.getRoomsForUser());
    }

    /**
     * Get doors for user response entity.
     *
     * @return the response entity
     */
    @GetMapping("simulation/doors-for-user")
    public ResponseEntity<?> getDoorsForUser(){
        return ResponseEntity.ok().body(repositoryService.getDoorsForUser());
    }

    /**
     * Toggle light response entity.
     *
     * @param light the light
     * @return the response entity
     */
    @PutMapping("simulation/toggleLight")
    public ResponseEntity<?> toggleLight(@Valid @RequestBody Light light){
        light.setTurnedOn(!light.isTurnedOn());
        Light newLight = this.repositoryService.saveLight(light);

        return ResponseEntity.ok().body(newLight);
    }

    /**
     * Toggle window response entity.
     *
     * @param window the window
     * @param action the action
     * @return the response entity
     */
    @PutMapping("simulation/toggleWindow/{action}")
    public ResponseEntity<?> toggleWindow(@Valid @RequestBody Window window, @PathVariable String action){
        if (action.equalsIgnoreCase("block"))
            window.setBlocked(!window.isBlocked());
        else{
            if (window.isBlocked())
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

            window.setOpen(!window.isOpen());
        }

        Window newWindow = this.repositoryService.saveWindow(window);
        return ResponseEntity.ok().body(newWindow);
    }

    /**
     * Toggle door response entity.
     *
     * @param door   the door
     * @param action the action
     * @return the response entity
     */
    @PutMapping("simulation/toggleDoor/{action}")
    public ResponseEntity<?> toggleDoor(@Valid @RequestBody Door door, @PathVariable String action){
        if (action.equalsIgnoreCase("lock"))
            door.setLocked(!door.isLocked());
        else{
            if (door.isLocked())
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            door.setOpen(!door.isOpen());
        }
        Door newDoor = this.repositoryService.saveDoor(door);
        return ResponseEntity.ok().body(newDoor);
    }

    /**
     * Place user response entity.
     *
     * @param roomID         the room id
     * @param simulationUser the simulation user
     * @return the response entity
     */
    @PutMapping("simulation/addUserToRoom/{roomID}")
    public ResponseEntity<?> placeUser(@PathVariable Long roomID, @RequestBody SimulationUser simulationUser){
        repositoryService.addUser(roomID, simulationUser);
        Simulation currentSimulation = simulationRepository.findById((long) 1);
        if (currentSimulation.notifyObserver() == -1) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Someone has intruded");
        }
        currentSimulation.setAutoMode();
        return ResponseEntity.ok().build();
    }

    /**
     * Remove user response entity.
     *
     * @param roomID         the room id
     * @param simulationUser the simulation user
     * @return the response entity
     */
    @PutMapping("simulation/removeUsersFromRoom/{roomID}")
    public ResponseEntity<?> removeUser(@PathVariable Long roomID, @RequestBody SimulationUser simulationUser){
        Simulation currentSimulation = simulationRepository.findById((long) 1);
        repositoryService.removeUser(roomID, simulationUser);
        currentSimulation.setAutoMode();
        return ResponseEntity.ok().build();
    }

    /**
     * Away mode lights response entity.
     *
     * @param lights the lights
     * @return the response entity
     */
    @PostMapping("simulation/awaymode-lights")
    public ResponseEntity<?> awayModeLights(@RequestBody AwayModeLights lights){
        System.out.println(lights);
        Simulation simulation = simulationRepository.findById((long) 1);
        List<Light> lightList = repositoryService.getLightsById(lights.getChecked());
        simulation.setChosenAwayModeLights(lightList);
        return ResponseEntity.ok().build();
    }

    @GetMapping("simulation/all-rooms")
    public ResponseEntity<?> allRooms(){
        List<Room> rooms = repositoryService.getAllRooms();
        return ResponseEntity.ok().body(rooms);
    }
}
