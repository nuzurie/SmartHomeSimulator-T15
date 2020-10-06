package com.soen343.SmartHomeSimulator.web;

import com.soen343.SmartHomeSimulator.model.Home;
import com.soen343.SmartHomeSimulator.model.HomeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api")

class HomeController {

    private final Logger log = LoggerFactory.getLogger(HomeController.class);
    private HomeRepository homeRepository;

    public HomeController(HomeRepository homeRepository) {
        this.homeRepository = homeRepository;
    }

    @GetMapping("/homes")
    Collection<Home> homes() {
        return homeRepository.findAll();
    }

    @GetMapping("/home/{id}")
    ResponseEntity<?> getHome(@PathVariable Long id) {
        Optional<Home> home = homeRepository.findById(id);
        return home.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/home")
    ResponseEntity<Home> createHome(@Valid @RequestBody Home home) throws URISyntaxException {
        log.info("Request to create home: {}", home);
        Home result = homeRepository.save(home);
        return ResponseEntity.created(new URI("/api/home/" + result.getId()))
                .body(result);
    }

    @PutMapping("/home/{id}")
    ResponseEntity<Home> updateHome(@Valid @RequestBody Home home) {
        log.info("Request to update home: {}", home);
        Home result = homeRepository.save(home);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/home/{id}")
    public ResponseEntity<?> deleteHome(@PathVariable Long id) {
        log.info("Request to delete home: {}", id);
        homeRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
