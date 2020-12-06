package com.soen343.SmartHomeSimulator.web;

import com.soen343.SmartHomeSimulator.model.SimulationUser;
import com.soen343.SmartHomeSimulator.model.repository.SimulationUserRepository;
import com.soen343.SmartHomeSimulator.module.simulation.model.Simulation;
import com.soen343.SmartHomeSimulator.module.simulation.repository.SimulationRepository;
import com.soen343.SmartHomeSimulator.service.SimulationUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.*;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * The Controller for a Simulation User.
 */
@Slf4j
@RestController
@RequestMapping("/api")
public class SimulationUserController {

    private SimulationUserRepository simulationUserRepository;
    private SimulationUserService simulationUserService;

    /**
     * Instantiates a new SimulationUserController.
     *
     * @param simulationUserRepository the simulation user repository
     * @param simulationUserService     the simulation user service
     */
    public SimulationUserController(SimulationUserRepository simulationUserRepository, SimulationUserService simulationUserService) {
        this.simulationUserRepository = simulationUserRepository;
        this.simulationUserService = simulationUserService;
    }

    /**
     * Gets users.
     *
     * @return the users
     */
    @GetMapping("/users")
    public Collection<SimulationUser> getUsers() {
        return simulationUserRepository.findAll();
    }

    /**
     * Gets user.
     *
     * @param id the id
     * @return the user
     */
    @GetMapping("/user/{id}")
    public ResponseEntity getUser(@PathVariable Long id) {
        SimulationUser user = simulationUserRepository.findById(id);
        log.info("The user with id {}", user);
        simulationUserRepository.findAll().forEach(System.out::println);

        if (user != null) {
            return ResponseEntity.ok().body(user);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Create user response entity.
     *
     * @param user the user
     * @return the response entity
     * @throws URISyntaxException the uri syntax exception
     */
    @PostMapping("/user")
    public ResponseEntity<SimulationUser> createUser(@Valid @RequestBody SimulationUser user) throws URISyntaxException {
        log.info("Request to create user: {}", user);
        SimulationUser created_user = simulationUserService.createNewUser(user);
        return ResponseEntity.ok().body(created_user);
    }

    /**
     * Update user response entity.
     *
     * @param user the user
     * @param id   the id
     * @return the response entity
     */
    @PutMapping("/user/{id}")
    ResponseEntity<SimulationUser> updateUser(@Valid @RequestBody SimulationUser user, @PathVariable Long id) {
        log.info("Request to update user: {}", user);
        SimulationUser simulationUser = simulationUserService.updateUser(user, id);
        return ResponseEntity.ok().body(simulationUser);

    }

    /**
     * Delete user response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        log.info("Request to delete User with id: {}", id);
        simulationUserService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Save users response entity.
     *
     * @return the response entity
     */
    @GetMapping("/users/save")
    public ResponseEntity saveUsers() {
        simulationUserService.saveUsers();
        return ResponseEntity.ok().build();
    }

    /**
     * Load users response entity.
     *
     * @return the response entity
     */
    @GetMapping("/users/load")
    public ResponseEntity loadUsers() {
        Set<SimulationUser> users = simulationUserService.loadUsers();
        return ResponseEntity.ok(users);
    }
}
