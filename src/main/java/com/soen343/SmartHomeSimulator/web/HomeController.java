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

/**
 * The Home Controller.
 */
@RestController
@RequestMapping("/api")

class HomeController {

    private final Logger log = LoggerFactory.getLogger(HomeController.class);
    private HomeRepository homeRepository;
    //private UserRepository userRepository;


    /**
     * Instantiates a new Home Controller.
     *
     * @param homeRepository the home repository
     */
    public HomeController(HomeRepository homeRepository) {
        this.homeRepository = homeRepository;
        //this.userRepository = userRepository;
    }

    /**
     * Collection of Homes.
     *
     * @param principal the principal
     * @return the collection
     */
    @GetMapping("/homes")
    Collection<Home> homes(Principal principal) {
        return homeRepository.findAllByUserId(principal.getName());
    }

    /**
     * Gets home.
     *
     * @param id the id
     * @return the home
     */
    @GetMapping("/home/{id}")
    ResponseEntity<?> getHome(@PathVariable Long id) {
        Home home = homeRepository.findById(id);
        if (home!=null){
            return ResponseEntity.ok().body(home);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Create group response entity.
     *
     * @param home      the home
     * @param principal the principal
     * @return the response entity
     * @throws URISyntaxException the uri syntax exception
     */
    @PostMapping("/home")
    ResponseEntity<Home> createHome(@Valid @RequestBody Home home,
                                    @AuthenticationPrincipal OAuth2User principal) throws URISyntaxException {
        log.info("Request to create home: {}", home);
        Map<String, Object> details = principal.getAttributes();
        String userId = details.get("sub").toString();

        // check to see if user already exists
//        Optional<User> user = userRepository.findById(userId);
//        home.setUser(user.orElse(new User(userId, details.get("name").toString())));

        Home result = homeRepository.save(home);
        return ResponseEntity.created(new URI("/api/home/" + result.getId()))
                .body(result);
    }


    /**
     * Update home response entity.
     *
     * @param home the home
     * @return the response entity
     */
    @PutMapping("/home/{id}")
    ResponseEntity<Home> updateHome(@Valid @RequestBody Home home) {
        log.info("Request to update home: {}", home);
        Home result = homeRepository.save(home);
        return ResponseEntity.ok().body(result);
    }

    /**
     * Delete home response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @DeleteMapping("/home/{id}")
    public ResponseEntity<?> deleteHome(@PathVariable Long id) {
        log.info("Request to delete home: {}", id);
        homeRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
