package com.soen343.SmartHomeSimulator;

import com.soen343.SmartHomeSimulator.model.Home;
import com.soen343.SmartHomeSimulator.model.SimulationUser;
import com.soen343.SmartHomeSimulator.model.repository.HomeRepository;
import com.soen343.SmartHomeSimulator.model.Room;
import com.soen343.SmartHomeSimulator.model.repository.SimulationUserRepository;
import com.soen343.SmartHomeSimulator.module.simulation.model.Simulation;
import com.soen343.SmartHomeSimulator.module.simulation.repository.SimulationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

@Component
class Initializer implements CommandLineRunner {

    private final HomeRepository repository;
    private final SimulationUserRepository simulationUserRepository;
    private final SimulationRepository simulationRepository;

    public Initializer(HomeRepository repository, SimulationUserRepository simulationUserRepository, SimulationRepository simulationRepository) {
        this.repository = repository;
        this.simulationUserRepository = simulationUserRepository;
        this.simulationRepository = simulationRepository;
    }

    @Override
    public void run(String... strings) {
        Stream.of("Main Home", "Summer House").forEach(name ->
                repository.save(new Home(name))
        );

        Set<Room.DoorWindow> windows = new HashSet<>();
        windows.add(Room.DoorWindow.builder().open(false).blocked(false).build());
        windows.add(Room.DoorWindow.builder().open(true).blocked(false).build());

        Set<Room.DoorWindow> door = new HashSet<>();
        door.add(Room.DoorWindow.builder().open(false).blocked(false).build());

        Set<Room.Lights> lights = new HashSet<>();
        lights.add(Room.Lights.builder().turnedOn(true).build());

        Home mainHome = repository.findByName("Main Home");
        Room livingRoom = Room.builder().name("Living Room")
                .size("12x24")
                .window(windows)
                .door(door)
                .lights(lights)
                .build();

        mainHome.setRooms(Collections.singleton(livingRoom));
        repository.save(mainHome);

        simulationUserRepository.save(SimulationUser.builder().name("Zubair").privilege("Parent").build());
        simulationUserRepository.save(SimulationUser.builder().name("Nouj").privilege("Child").build());
        simulationUserRepository.save(SimulationUser.builder().name("Yas").privilege("Child").build());

        repository.findAll().forEach(System.out::println);
        simulationUserRepository.findAll().forEach(System.out::println);


        Set<SimulationUser> simulationUsers = new HashSet<>(simulationUserRepository.findAll());

        Simulation simulation = Simulation.builder()
                .name("1")
                .date("2020-01-01")
                .home(mainHome)
                .time("03:00")
                .temperature(22.5)
                .simulationUser(simulationUsers)
                .build();

        System.out.println(simulationRepository.save(simulation));
    }
}
