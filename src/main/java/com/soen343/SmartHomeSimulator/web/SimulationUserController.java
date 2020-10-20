package com.soen343.SmartHomeSimulator.web;

import com.soen343.SmartHomeSimulator.model.SimulationUser;
import com.soen343.SmartHomeSimulator.model.repository.SimulationUserRepository;
import com.soen343.SmartHomeSimulator.module.simulation.model.Simulation;
import com.soen343.SmartHomeSimulator.module.simulation.repository.SimulationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api")
public class SimulationUserController {
    private SimulationUserRepository simulationUserRepository;
    private SimulationRepository simulationRepository;

    public SimulationUserController(SimulationUserRepository simulationUserRepository, SimulationRepository simulationRepository) {
        this.simulationUserRepository = simulationUserRepository;
        this.simulationRepository = simulationRepository;
    }

    @GetMapping("/users")
    public Collection<SimulationUser> getUsers() {
        return simulationUserRepository.findAll();
    }

    @GetMapping("/user/{id}")
    public ResponseEntity getUser(@PathVariable Long id){
        SimulationUser user = simulationUserRepository.findById(id);
        log.info("The user with id {}", user);
        simulationUserRepository.findAll().forEach(System.out::println);

        if (user!=null){
            return ResponseEntity.ok().body(user);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

//        return user.map(response -> ResponseEntity.ok().body(response))
//                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/user")
    public ResponseEntity<SimulationUser> createUser(@Valid @RequestBody SimulationUser user) throws URISyntaxException {
        log.info("Request to create user: {}", user);

        user.setId();
        SimulationUser created_user = simulationUserRepository.save(user);
        simulationRepository.findById(Long.valueOf(1)).getSimulationUsers().add(created_user);

        return ResponseEntity.ok().body(created_user);
    }

    @PutMapping("/user/{id}")
    ResponseEntity<SimulationUser> updateUser(@Valid @RequestBody SimulationUser user, @PathVariable Long id) {
        log.info("Request to update user: {}", user);
        SimulationUser simulationUser = simulationUserRepository.findById(id);

        simulationUser.setName(user.getName());
        simulationUser.setPrivilege(user.getPrivilege());

        return ResponseEntity.ok().body(simulationUser);

    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        log.info("Request to delete User with id: {}", id);
        SimulationUser simulationUser = simulationUserRepository.findById(id);
        simulationUserRepository.deleteById(id);
        Simulation simulation = simulationRepository.findById(Long.valueOf(1));
        simulation.deleteUser(simulationUser);

        return ResponseEntity.ok().build();
    }


}
