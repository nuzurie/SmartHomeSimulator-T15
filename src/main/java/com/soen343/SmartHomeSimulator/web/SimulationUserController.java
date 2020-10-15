package com.soen343.SmartHomeSimulator.web;

import com.soen343.SmartHomeSimulator.model.SimulationUser;
import com.soen343.SmartHomeSimulator.model.User;
import com.soen343.SmartHomeSimulator.model.repository.SimulationUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
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

    public SimulationUserController(SimulationUserRepository simulationUserRepository) {
        this.simulationUserRepository = simulationUserRepository;
    }

    @GetMapping("/users")
    public Collection<SimulationUser> getUsers() {
        return simulationUserRepository.findAll();
    }

    @GetMapping("/user/{id}")
    public ResponseEntity getUser(@PathVariable Long id){
        Optional<SimulationUser> user = simulationUserRepository.findById(id);
        log.info("The user with id {}", user.map(response -> response.getId()));
        simulationUserRepository.findAll().forEach(System.out::println);

        return user.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/user")
    public ResponseEntity<SimulationUser> createUser(@Valid @RequestBody SimulationUser user) throws URISyntaxException {
        log.info("Request to create home: {}", user);

        Optional<SimulationUser> alreadyExistsUser = simulationUserRepository.findById(user.getId());
        SimulationUser created_user = simulationUserRepository.save(user);
        return ResponseEntity.ok().body(created_user);
    }

    @PutMapping("/user/{id}")
    ResponseEntity<SimulationUser> updateUser(@Valid @RequestBody SimulationUser user) {
        log.info("Request to update home: {}", user);
        SimulationUser created_user = simulationUserRepository.save(user);
        return ResponseEntity.ok().body(created_user);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        log.info("Request to delete User with id: {}", id);
        simulationUserRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }


}
