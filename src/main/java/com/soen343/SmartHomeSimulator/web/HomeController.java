package com.soen343.SmartHomeSimulator.web;

import com.soen343.SmartHomeSimulator.model.Home;
import com.soen343.SmartHomeSimulator.model.User;
import com.soen343.SmartHomeSimulator.model.repository.HomeRepository;
import com.soen343.SmartHomeSimulator.model.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.security.Principal;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")

class HomeController {

    private final Logger log = LoggerFactory.getLogger(HomeController.class);
    private HomeRepository homeRepository;
    private UserRepository userRepository;


    public HomeController(HomeRepository homeRepository, UserRepository userRepository) {
        this.homeRepository = homeRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/homes")
    Collection<Home> groups(Principal principal) {
        return homeRepository.findAllByUserId(principal.getName());
    }

    @GetMapping("/home/{id}")
    ResponseEntity<?> getHome(@PathVariable Long id) {
        Optional<Home> home = homeRepository.findById(id);
        return home.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/home")
    ResponseEntity<Home> createGroup(@Valid @RequestBody Home home,
                                      @AuthenticationPrincipal OAuth2User principal) throws URISyntaxException {
        log.info("Request to create home: {}", home);
        Map<String, Object> details = principal.getAttributes();
        String userId = details.get("sub").toString();

        // check to see if user already exists
        Optional<User> user = userRepository.findById(userId);
        home.setUser(user.orElse(new User(userId, details.get("name").toString(), details.get("type").toString())));

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
