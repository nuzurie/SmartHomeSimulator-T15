package com.soen343.SmartHomeSimulator;

import com.soen343.SmartHomeSimulator.model.Home;
import com.soen343.SmartHomeSimulator.model.repository.HomeRepository;
import com.soen343.SmartHomeSimulator.model.Room;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.stream.Stream;

@Component
class Initializer implements CommandLineRunner {

    private final HomeRepository repository;

    public Initializer(HomeRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... strings) {
        Stream.of("Main Home", "Summer House").forEach(name ->
                repository.save(new Home(name))
        );

        Home mainHome = repository.findByName("Main Home");
        Room livingRoom = Room.builder().name("Living Room")
                .size("12x24")
                .lights(1)
                .windows(3)
                .build();
        mainHome.setRooms(Collections.singleton(livingRoom));
        repository.save(mainHome);

        repository.findAll().forEach(System.out::println);
    }
}
